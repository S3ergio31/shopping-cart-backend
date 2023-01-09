package com.shoppingcart.backend.repositories;

import com.shoppingcart.backend.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    public boolean existsByName(String name);

    public CategoryEntity findByName(String name);
}
