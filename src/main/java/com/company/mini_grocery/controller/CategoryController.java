package com.company.mini_grocery.controller;

import com.company.mini_grocery.dto.request.CategoryRequest;
import com.company.mini_grocery.dto.response.CategoryResponse;
import com.company.mini_grocery.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request){
        return  categoryService.createCategory(request);
    }

    @GetMapping
    public List<CategoryResponse> getCategory( ){
        return categoryService.getCategory();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }
}
