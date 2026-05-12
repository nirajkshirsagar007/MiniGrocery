package com.company.mini_grocery.service.impl;

import com.company.mini_grocery.dto.request.ProductRequest;
import com.company.mini_grocery.dto.response.ProductResponse;
import com.company.mini_grocery.entity.Category;
import com.company.mini_grocery.entity.Product;
import com.company.mini_grocery.exception.CategoryNotFoundException;
import com.company.mini_grocery.exception.InsuffecientStockException;
import com.company.mini_grocery.exception.ProductNotFoundException;
import com.company.mini_grocery.repository.CategoryRepository;
import com.company.mini_grocery.repository.ProductRepository;
import com.company.mini_grocery.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse createProduct(ProductRequest request){
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));

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
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById( id)
                .orElseThrow(()->new ProductNotFoundException("Product not found with id: "+ id));
        return mapToResponse(product);

    }

    public ProductResponse updateProduct(Long id, ProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found."));

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
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductResponse addStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product Not found with id: "+ id));

        product.setQuantity(product.getQuantity() + quantity);
        return  mapToResponse(productRepository.save(product));
    }

    public ProductResponse reduceStock(Long id, Integer quantity){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: "+ id ));

        if(product.getQuantity() < quantity){
            throw new InsuffecientStockException("Insuffecient Stock ");
        }

        product.setQuantity(product.getQuantity() - quantity);

        return mapToResponse(productRepository.save(product));
    }

    public List<ProductResponse> getLowStockProducts(Integer threshold){

        return  productRepository.findAll()
                .stream()
                .filter(product -> product.getQuantity() <= threshold)
                .map(this::mapToResponse)
                .toList();
    }

    public List<ProductResponse> getExpiredProducts(){
        LocalDate today = LocalDate.now();

        return  productRepository.findAll()
                .stream()
                .filter(product -> product.getExpiryDate() != null &&
                        product.getExpiryDate().isBefore(today))
                .map(this::mapToResponse)
                .toList();
    }

    public Page<ProductResponse>getProducts(int page, int size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public Page<ProductResponse> searchProducts(
            String name,
            int page,
            int size){
        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
    }

    public Page<ProductResponse> getProductByCategoryId(
            Long categoryId,
            int page,
            int size){
        Pageable pageable = PageRequest.of(page,size);
        return productRepository.findByCategoryId(categoryId,pageable)
                .map(this::mapToResponse);
    }

    public Page<ProductResponse> getProductByCategoryName(
            String categoryName,
            int page,
            int size){
        Pageable pageable = PageRequest.of(page,size);

        return productRepository.findByCategory_Name(
                categoryName,
                pageable
        );
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
