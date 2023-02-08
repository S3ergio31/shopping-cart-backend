package com.shoppingcart.backend.repositories;

import com.shoppingcart.backend.domain.Product;
import lombok.Data;

@Data
public class FakeStoreProduct extends Product {
    private Rate rating;

    public Double getRate(){
        return this.rating.getRate();
    }

    public Integer getCount(){
        return this.rating.getCount();
    }
}
