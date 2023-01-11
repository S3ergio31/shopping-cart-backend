package com.shoppingcart.backend.services;

import com.google.common.reflect.TypeToken;
import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.entities.CategoryEntity;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Iterable<Category> all(){
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        Type listType = new TypeToken<List<CategoryEntity>>() {}.getType();
        return modelMapper.map(categories, listType);
    }

    public Category find(Long id) throws CategoryNotFound{
        CategoryEntity category =
        categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFound(id)
        );
        return modelMapper.map(category, Category.class);
    }

    public boolean existsByName(String name){
        return categoryRepository.existsByName(name);
    }

    public Category findByName(String name){
        CategoryEntity categoryEntity = categoryRepository.findByName(name);
        return modelMapper.map(categoryEntity, Category.class);
    }

    public Category upsert(Category category) {
        CategoryEntity categoryEntity = categoryRepository.save(
            modelMapper.map(category, CategoryEntity.class)
        );
        return modelMapper.map(categoryEntity, Category.class);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
