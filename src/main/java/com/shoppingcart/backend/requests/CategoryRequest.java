package com.shoppingcart.backend.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryRequest {
    private Long id;

    @NotEmpty(message = "category name cannot be null")
    private String name;
}
