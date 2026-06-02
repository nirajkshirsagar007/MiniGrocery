package com.company.mini_grocery.controller;

import com.company.mini_grocery.common.ApiResponse;
import com.company.mini_grocery.dto.request.ProductRequest;
import com.company.mini_grocery.dto.request.UpdateStockRequest;
import com.company.mini_grocery.dto.response.ProductResponse;
import com.company.mini_grocery.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product APIs", description = "Operations related to products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Create product")
    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){

        ProductResponse productResponse = productService.createProduct(request);
        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Product created successfully")
                .data(productResponse)
                .build();
    }

    @Operation(summary = "Get all products")
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> response =  productService.getAllProducts();
        return ApiResponse.<List<ProductResponse>>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(response)
                .build();
    }

    @Operation(summary = "Get products by id")
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id){

        ProductResponse response = productService.getProductById(id);

        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(response)
                .build();
    }

    @Operation(summary = "update Product By Id")
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
    @Operation(summary = "Delete Product By Id")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductById(@PathVariable Long id){
        productService.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Product deleted successfully")
                .data(null)
                .build();
    }
    @Operation(summary = "Add product to stock")
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

    @Operation(summary = "Reduce product from stock")
    @PatchMapping("/{id}/reduce-stock")
    public ApiResponse<ProductResponse> reduceStock(@PathVariable Long id,@Valid @RequestBody UpdateStockRequest request){
        ProductResponse response = productService.reduceStock(id,request.getQuantity());

        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Stock reduced successfully")
                .data(response)
                .build();
    }

    @Operation(summary = "Get low stock product")
    @GetMapping("/low-stock")
    public ApiResponse<List<ProductResponse>> getLowStockProduct(@RequestParam Integer threshold){
        List<ProductResponse> response = productService.getLowStockProducts(threshold);

        return ApiResponse.<List<ProductResponse>>builder()
                .success(true)
                .message("Low stock fetched successfully")
                .data(response)
                .build();
    }
    @Operation(summary = "Get expired products")
    @GetMapping("/expired")
    public ApiResponse<List<ProductResponse>> getExpiredProducts(){

        List<ProductResponse> response = productService.getExpiredProducts();
        return ApiResponse.<List<ProductResponse>>builder()
                .success(true)
                .message("Expired products fetched successfully")
                .data(response)
                .build();
    }

    @Operation(summary = "get products pagewise")
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

    @Operation(summary = "Search products")
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

    @Operation(summary = "Get product by category id")
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

    @Operation(summary = "Get product by category name")
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
