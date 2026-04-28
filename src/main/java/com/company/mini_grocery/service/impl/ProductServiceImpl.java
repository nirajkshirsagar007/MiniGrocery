package com.company.mini_grocery.service.impl;

import com.company.mini_grocery.dto.request.CreateProductRequest;
import com.company.mini_grocery.dto.response.ProductResponse;
import com.company.mini_grocery.entity.Category;
import com.company.mini_grocery.entity.Product;
import com.company.mini_grocery.repository.CategoryRepository;
import com.company.mini_grocery.repository.ProductRepository;
import com.company.mini_grocery.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse createProduct(CreateProductRequest request){
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .expiryDate(request.getExpiryDate())
                .barcode(request.getBarcode())
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById( id)
                .orElseThrow(()->new RuntimeException("Product not found"));
        return mapToResponse(product);

    }

    private ProductResponse mapToResponse(Product product){
       return ProductResponse.builder()
               .id(product.getId())
               .name(product.getName())
               .price(product.getPrice())
               .quantity(product.getQuantity())
               .expiryDate(product.getExpiryDate())
               .barcode(product.getBarcode())
               .categoryId(product.getCategory().getId())
               .categoryName(product.getCategory().getName())
               .build();
    }
}
