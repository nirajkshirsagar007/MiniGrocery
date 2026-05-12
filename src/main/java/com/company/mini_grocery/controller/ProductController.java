package com.company.mini_grocery.controller;

import com.company.mini_grocery.common.ApiResponse;
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
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){

        ProductResponse productResponse = productService.createProduct(request);
        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Product created successfully")
                .data(productResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> response =  productService.getAllProducts();
        return ApiResponse.<List<ProductResponse>>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id){

        ProductResponse response = productService.getProductById(id);

        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProductById(@PathVariable Long id,
                                             @Valid @RequestBody ProductRequest request){
        ProductResponse response = productService.updateProduct(id,request);
        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Product updated successfully")
                .data(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductById(@PathVariable Long id){
        productService.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Product deleted successfully")
                .data(null)
                .build();
    }

    @PatchMapping("/{id}/add-stock")
    public ApiResponse<ProductResponse> addStock(@PathVariable Long id,
                                    @Valid @RequestBody UpdateStockRequest request) {
        ProductResponse response = productService.addStock(id,request.getQuantity());
        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Stock added successfully")
                .data(response)
                .build();
    }

    @PatchMapping("/{id}/reduce-stock")
    public ApiResponse<ProductResponse> reduceStock(@PathVariable Long id,@Valid @RequestBody UpdateStockRequest request){
        ProductResponse response = productService.reduceStock(id,request.getQuantity());

        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Stock reduced successfully")
                .data(response)
                .build();
    }

    @GetMapping("/low-stock")
    public ApiResponse<List<ProductResponse>> getLowStockProduct(@RequestParam Integer threshold){
        List<ProductResponse> response = productService.getLowStockProducts(threshold);

        return ApiResponse.<List<ProductResponse>>builder()
                .success(true)
                .message("Low stock fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/expired")
    public ApiResponse<List<ProductResponse>> getExpiredProducts(){

        List<ProductResponse> response = productService.getExpiredProducts();
        return ApiResponse.<List<ProductResponse>>builder()
                .success(true)
                .message("Expired products fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/paginated")
    public ApiResponse<Page<ProductResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){

        Page<ProductResponse> response =  productService.getProducts(page,size,sortBy,sortDir);

        return ApiResponse.<Page<ProductResponse>>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<ProductResponse>> searchProducts(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Page<ProductResponse> response = productService.searchProducts(name, page, size);
        return ApiResponse.<Page<ProductResponse>>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<Page<ProductResponse>> getProductByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){

        Page<ProductResponse> response =  productService.getProductByCategoryId(categoryId, page, size);
        return ApiResponse.<Page<ProductResponse>>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/category/name/{categoryName}")
    public ApiResponse<Page<ProductResponse>> getProductByCategoryName(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Page<ProductResponse> response = productService.getProductByCategoryName(categoryName, page, size);
        return ApiResponse.<Page<ProductResponse>>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(response)
                .build();
    }
}
