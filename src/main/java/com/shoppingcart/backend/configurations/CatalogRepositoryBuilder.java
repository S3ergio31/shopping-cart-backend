package com.shoppingcart.backend.configurations;

import com.shoppingcart.backend.repositories.CatalogApiRepository;
import com.shoppingcart.backend.repositories.CatalogRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogRepositoryBuilder {

    public CatalogRepository catalogApiRepository(){
        return new CatalogApiRepository();
    }
}
