package com.shoppingcart.backend.controllers;

import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.requests.CategoryRequest;
import com.shoppingcart.backend.services.CategoryService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("categories")
@Api(tags={"Categories"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Iterable<Category> all(){
        return categoryService.all();
    }

    @GetMapping("/{id}")
    public Category find(@PathVariable Long id) throws CategoryNotFound {
        return categoryService.find(id);
    }

    @PutMapping
    public Category upsert(@RequestBody @Valid CategoryRequest categoryRequest) throws CategoryNotFound {
        Category category = modelMapper.map(categoryRequest, Category.class);
        return categoryService.upsert(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws CategoryNotFound {
        categoryService.delete(id);
    }
}
