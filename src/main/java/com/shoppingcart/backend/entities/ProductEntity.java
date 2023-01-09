package com.shoppingcart.backend.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false, length = 1000)
    private String description;
    @OneToOne
    private CategoryEntity category;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private Double rate;
    @Column(nullable = false)
    private Integer count;
}
