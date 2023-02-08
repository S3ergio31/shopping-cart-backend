package com.shoppingcart.backend.requests;

import com.shoppingcart.backend.services.CategoryService;
import com.shoppingcart.backend.validations.Unique;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
public class CategoryRequest {
    private Long id;
    @NotBlank(message = "Category name cannot be empty")
    @NotEmpty(message = "Category name cannot be empty")
    @Unique(uniqueSource = CategoryService.class)
    private String name;
}
