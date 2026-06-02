package com.company.mini_grocery.controller;

import com.company.mini_grocery.dto.request.CategoryRequest;
import com.company.mini_grocery.dto.response.CategoryResponse;
import com.company.mini_grocery.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Create category")
    @PostMapping
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request){
        return  categoryService.createCategory(request);
    }

    @Operation(summary = "Get category")
    @GetMapping
    public List<CategoryResponse> getCategory( ){
        return categoryService.getCategory();
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Update category")
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryRequest request){
        return categoryService.updateCategory(id, request);
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id){

        categoryService.deleteCategory(id);
        return "Category deleted successfully";
    }
}
