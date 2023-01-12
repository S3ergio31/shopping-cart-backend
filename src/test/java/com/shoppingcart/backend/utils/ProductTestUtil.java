package com.shoppingcart.backend.utils;

import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.domain.Rating;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTestUtil {
    public Product getProduct(){
        Product product = new Product();
        Category category = new Category();
        Rating rating = new Rating();
        category.setId(1L);
        category.setName("Category test");
        rating.setRate(3.9);
        rating.setCount(14);
        product.setRating(rating);
        product.setCategory(category);
        product.setId(1L);
        product.setPrice(14.99);
        product.setDescription("Product description");
        product.setTitle("Product title");
        product.setImage("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg");
        return product;
    }

    public void assertProduct(Product expected, Product current){
        assertEquals(expected.getId(), current.getId());
        assertEquals(expected.getPrice(), current.getPrice());
        assertEquals(expected.getTitle(), current.getTitle());
        assertEquals(expected.getDescription(), current.getDescription());
        assertEquals(expected.getImage(), current.getImage());
        assertEquals(expected.getCategory().getId(), current.getCategory().getId());
        assertEquals(expected.getCategory().getName(), current.getCategory().getName());
        assertEquals(expected.getRating().getRate(), current.getRating().getRate());
        assertEquals(expected.getRating().getCount(), current.getRating().getCount());
    }
}
