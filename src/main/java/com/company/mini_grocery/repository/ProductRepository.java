package com.company.mini_grocery.repository;

import com.company.mini_grocery.dto.response.ProductResponse;
import com.company.mini_grocery.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<Product> findByCategoryId(
            Long CategoryId,
            Pageable pageable
    );

    Page<ProductResponse> findByCategory_Name(
            String categoryName,
            Pageable pageable
    );

}
