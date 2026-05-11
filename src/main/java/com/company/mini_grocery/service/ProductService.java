package com.company.mini_grocery.service;

import com.company.mini_grocery.dto.request.ProductRequest;
import com.company.mini_grocery.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    List<ProductResponse> getAllProduct();
    ProductResponse getProductById(Long id);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
    ProductResponse addStock(Long id, Integer quantity);
    ProductResponse reduceStock(Long id, Integer quantity);
    List<ProductResponse> getLowStockProduct(Integer threshold);
    List<ProductResponse> getExpiredProducts();

    Page<ProductResponse> getProducts(
            int page,
            int size,
            String sortBy,
            String sortDir);

    Page<ProductResponse> searchProducts(
            String name,
            int page,
            int size);

    Page<ProductResponse> getProductByCategoryId(
            Long categoryId,
            int page,
            int size);

    Page<ProductResponse> getProductByCategoryName(
            String name,
            int page,
            int size
    );
}
