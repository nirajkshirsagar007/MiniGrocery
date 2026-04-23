package com.company.mini_grocery.repository;

import com.company.mini_grocery.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
