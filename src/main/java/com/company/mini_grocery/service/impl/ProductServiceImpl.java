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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse createProduct(ProductRequest request){

        log.info("Creating product with name: {}",request.getName());
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
        log.info("Product created successfully with id: {}",saved.getId());
        return mapToResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        log.info("Product fetched successfully.");
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        log.info("Product fetched successfully with id: {}",id);

        Product product = productRepository.findById( id)
                .orElseThrow(()->new ProductNotFoundException("Product not found with id: "+ id));
        return mapToResponse(product);

    }

    public ProductResponse updateProduct(Long id, ProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> { log.error("product not found with id: {}",id);
                    return new ProductNotFoundException("Product not found");
                });

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()->{ log.error("category not found with id: {}",request.getCategoryId());
                        return new CategoryNotFoundException("Category not found.");});

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setExpiryDate(request.getExpiryDate());
        product.setBarcode(request.getBarcode());

        Product updated = productRepository.save(product);
        log.info("Product updated successfully with id: {}",updated.getId());
        return mapToResponse(updated);
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
        log.info("Product deleted successfully with id: {}",id);
    }

    @Override
    public ProductResponse addStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product Not found with id: "+ id));

        product.setQuantity(product.getQuantity() + quantity);
        log.info("Product added to the stock successfully.",id);
        return  mapToResponse(productRepository.save(product));
    }

    public ProductResponse reduceStock(Long id, Integer quantity){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: "+ id ));

        if(product.getQuantity() < quantity){
            throw new InsuffecientStockException("Insufficient Stock ");
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
