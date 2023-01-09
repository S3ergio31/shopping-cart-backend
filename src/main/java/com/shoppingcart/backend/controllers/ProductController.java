package com.shoppingcart.backend.controllers;

import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public Iterable<Product> all(){
        return productService.all();
    }
    @GetMapping("/{id}")
    public Product find(@PathVariable Long id){
        return productService.find(id);
    }
    @PutMapping
    public Product upsert(@RequestBody Product product) {
        return productService.upsert(product);
    }
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        productService.delete(id);
        return true;
    }
}
