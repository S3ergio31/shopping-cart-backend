package com.shoppingcart.backend.exceptions;

import org.springframework.util.StringUtils;

public class EntityNotFound extends Exception {
    public EntityNotFound(Long id, String name){
        super(StringUtils.capitalize(name) + " with id=" + id.toString() + " not found");
    }
}
