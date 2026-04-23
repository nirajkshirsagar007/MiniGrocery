package com.company.mini_grocery.service;

import com.company.mini_grocery.dto.request.CategoryRequest;
import com.company.mini_grocery.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);
    List<CategoryResponse> getCategory();
    CategoryResponse getCategoryById(Long id);

}
