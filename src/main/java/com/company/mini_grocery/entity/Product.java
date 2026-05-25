package com.company.mini_grocery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    private LocalDate expiryDate;

    @Column(unique = true)
    private String barcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
}
