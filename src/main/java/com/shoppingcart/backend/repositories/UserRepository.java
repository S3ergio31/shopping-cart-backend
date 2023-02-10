package com.shoppingcart.backend.repositories;

import com.shoppingcart.backend.domain.User;
import com.shoppingcart.backend.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    public boolean existsByEmail(String email);

    public UserEntity findByEmail(String email);
}
