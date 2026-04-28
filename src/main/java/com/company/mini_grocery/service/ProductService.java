package com.company.mini_grocery.service;

import com.company.mini_grocery.dto.request.CreateProductRequest;
import com.company.mini_grocery.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    List<ProductResponse> getAllProduct();
    ProductResponse getProductById(Long id);
}
