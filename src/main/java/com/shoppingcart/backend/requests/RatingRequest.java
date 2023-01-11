package com.shoppingcart.backend.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RatingRequest {
    @NotNull(message = "rating rate cannot be null")
    private Double rate;

    @NotNull(message = "rating count cannot be null")
    private Integer count;
}
