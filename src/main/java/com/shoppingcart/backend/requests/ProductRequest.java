package com.shoppingcart.backend.requests;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductRequest {
    @NotEmpty(message = "product title cannot be null")
    private String title;

    @NotNull(message = "product price cannot be null")
    private Double price;

    @NotEmpty(message = "product description cannot be null")
    private String description;

    @NotNull(message = "product category cannot be null")
    private CategoryRequest category;

    @NotEmpty(message = "product image cannot be null")
    @URL(message = "product image must be a url")
    private String image;

    @NotNull(message = "product rating cannot be null")
    private RatingRequest rating;
}
