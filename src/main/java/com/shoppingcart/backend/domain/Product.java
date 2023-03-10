package com.shoppingcart.backend.domain;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private Category category;
    private String image;
    private Double rate;
    private Integer count;

}
