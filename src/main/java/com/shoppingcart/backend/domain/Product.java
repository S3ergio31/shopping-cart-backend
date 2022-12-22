package com.shoppingcart.backend.domain;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private Category category;
    private String image;
    private Rating rating;
}
