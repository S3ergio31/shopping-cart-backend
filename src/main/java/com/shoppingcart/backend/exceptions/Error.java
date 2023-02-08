package com.shoppingcart.backend.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Error {
    private String message;

    private int status;
}
