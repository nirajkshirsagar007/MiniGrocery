package com.company.mini_grocery.service.impl;

import com.company.mini_grocery.dto.request.ProductRequest;
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

    public ProductResponse createProduct(ProductRequest request){
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

    public ProductResponse updateProduct(Long id, ProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not found."));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setExpiryDate(request.getExpiryDate());
        product.setBarcode(request.getBarcode());

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
        productRepository.delete(product);
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
