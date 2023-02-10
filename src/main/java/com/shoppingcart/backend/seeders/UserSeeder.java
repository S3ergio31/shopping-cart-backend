package com.shoppingcart.backend.seeders;

import com.shoppingcart.backend.domain.User;
import com.shoppingcart.backend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserSeeder {
    @Autowired
    private UserService userService;

    @Bean
    public void users(){
        createUser("User", "password", "user@test.com");
        createUser("Admin", "password", "admin@test.com");
    }

    private void createUser(String name, String password, String email){
        if(userService.existsByEmail(email)){
            return;
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userService.create(user);
        log.info("Created user " + name);
    }
}
