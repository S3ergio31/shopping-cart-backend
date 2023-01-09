package com.shoppingcart.backend.repositories;

import com.shoppingcart.backend.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    public boolean existsByTitle(String title);
}
