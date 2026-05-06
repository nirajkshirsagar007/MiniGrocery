package com.company.mini_grocery.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message)
    {
        super(message);
    }
}
