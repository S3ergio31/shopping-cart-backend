package com.shoppingcart.backend.exceptions;

public class CategoryNotFound extends EntityNotFound{
    public CategoryNotFound(Long id){
        super(id, "category");
    }
}
