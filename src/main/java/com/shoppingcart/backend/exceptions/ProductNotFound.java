package com.shoppingcart.backend.exceptions;

public class ProductNotFound extends EntityNotFound{
    public ProductNotFound(Long id){
        super(id, "product");
    }
}