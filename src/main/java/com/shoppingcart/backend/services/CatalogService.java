package com.shoppingcart.backend.services;

import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {
    @Autowired
    private CatalogRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.getProducts();
    }

    public List<Category> getCategories(){
        return productRepository.getCategories();
    }
}
