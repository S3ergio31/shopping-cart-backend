package com.shoppingcart.backend.requests;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductRequest {
    private Long id;
    @NotEmpty(message = "Product title cannot be empty")
    private String title;

    @NotNull(message = "Product price cannot be empty")
    private Double price;

    @NotEmpty(message = "Product description cannot be empty")
    private String description;

    @NotNull(message = "Product category cannot be empty")
    private CategoryRequest category;

    @NotEmpty(message = "Product image cannot be empty")
    @URL(message = "Product image must be a url")
    private String image;

    @NotNull(message = "Product rate cannot be empty")
    @Min(0)
    private Double rate;

    @NotNull(message = "Product count cannot be empty")
    @Min(0)
    private Integer count;
}
