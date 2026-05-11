package com.company.mini_grocery.controller;

import com.company.mini_grocery.dto.request.ProductRequest;
import com.company.mini_grocery.dto.request.UpdateStockRequest;
import com.company.mini_grocery.dto.response.ProductResponse;
import com.company.mini_grocery.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request){
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProductById(@PathVariable Long id,
                                             @Valid @RequestBody ProductRequest request){
        return  productService.updateProduct(id,request);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable Long id){
        productService.deleteProduct(id);
        return "Product deleted Successfully";
    }

    @PatchMapping("/{id}/add-stock")
    public ProductResponse addStock(@PathVariable Long id,
                                    @Valid @RequestBody UpdateStockRequest request) {
        return  productService.addStock(id,request.getQuantity());
    }

    @PatchMapping("/{id}/reduce-stock")
    public ProductResponse reduceStock(@PathVariable Long id,@Valid @RequestBody UpdateStockRequest request){
        return  productService.reduceStock(id,request.getQuantity());
    }

    @GetMapping("/low-stock")
    public List<ProductResponse> getLowStockProduct(@RequestParam Integer threshold){
        return productService.getLowStockProduct(threshold);
    }

    @GetMapping("/expired")
    public List<ProductResponse> getExpiredProducts(){
        return productService.getExpiredProducts();
    }

    @GetMapping("/paginated")
    public Page<ProductResponse> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){

        return productService.getProducts(page,size,sortBy,sortDir);
    }

    @GetMapping("/search")
    public Page<ProductResponse> searchProducts(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        return productService.searchProducts(name, page, size);
    }

    @GetMapping("/category/{categoryId}")
    public Page<ProductResponse> getProductByCategory(
            @PathVariable Long categoryId,
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        return productService.getProductByCategoryId(
                categoryId,
                page,
                size
        );
    }

    @GetMapping("/category/name/{categoryName}")
    public Page<ProductResponse> getProductByCategoryName(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        return productService.getProductByCategoryName(
                categoryName,
                page,
                size
        );
    }
}
