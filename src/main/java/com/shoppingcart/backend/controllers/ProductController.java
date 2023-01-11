package com.shoppingcart.backend.controllers;

import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.exceptions.ProductNotFound;
import com.shoppingcart.backend.requests.ProductRequest;
import com.shoppingcart.backend.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Iterable<Product> all(){
        return productService.all();
    }

    @GetMapping("/{id}")
    public Product find(@PathVariable Long id) throws ProductNotFound {
        return productService.find(id);
    }

    @PutMapping
    public Product upsert(@Valid @RequestBody ProductRequest productRequest) throws CategoryNotFound {
        Product product = modelMapper.map(productRequest, Product.class);
        return productService.upsert(product);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) throws ProductNotFound {
        productService.delete(id);
        return true;
    }
}
