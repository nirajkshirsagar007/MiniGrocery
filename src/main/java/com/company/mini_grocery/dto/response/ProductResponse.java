package com.company.mini_grocery.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private LocalDate expiryDate;
    private String barcode;
    private Long categoryId;
    private String categoryName;
}
