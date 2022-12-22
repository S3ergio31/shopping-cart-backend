package com.shoppingcart.backend.controllers;

import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("categories")
    public List<Category> getCategories(){
        return productService.getCategories();
    }
}
