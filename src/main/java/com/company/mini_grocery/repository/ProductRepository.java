package com.company.mini_grocery.repository;

import com.company.mini_grocery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
