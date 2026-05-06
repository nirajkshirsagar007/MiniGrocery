package com.company.mini_grocery.exception;

public class InsuffecientStockException extends RuntimeException {
    public InsuffecientStockException(String message) {
        super(message);
    }
}
