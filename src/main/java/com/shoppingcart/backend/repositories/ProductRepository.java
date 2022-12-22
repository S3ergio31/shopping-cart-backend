package com.shoppingcart.backend.repositories;

import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {
    public List<Product> getProducts();
    public List<Category> getCategories();
}
