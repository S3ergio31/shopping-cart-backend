package com.shoppingcart.backend.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
public class CategoryRequest {
    private Long id;
    @NotBlank(message = "Category name cannot be empty")
    @NotEmpty(message = "Category name cannot be empty")
    private String name;
}
