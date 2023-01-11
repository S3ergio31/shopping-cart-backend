package com.shoppingcart.backend.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryRequest {
    @NotEmpty(message = "category id cannot be null")
    private Long id;

    @NotEmpty(message = "category name cannot be null")
    private String name;
}
