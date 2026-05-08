package com.company.mini_grocery.service;

import com.company.mini_grocery.dto.request.ProductRequest;
import com.company.mini_grocery.dto.response.ProductResponse;

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
}
