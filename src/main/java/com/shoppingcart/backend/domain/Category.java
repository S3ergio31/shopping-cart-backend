package com.shoppingcart.backend.domain;

import lombok.Data;

@Data
public class Category {
    private String name;

    private Long id;

    public Category(){}

    public Category(String name) {
        this.name = name;
    }
}
