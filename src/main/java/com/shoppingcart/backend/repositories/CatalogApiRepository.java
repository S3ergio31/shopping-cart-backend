package com.shoppingcart.backend.repositories;

import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.domain.Product;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CatalogApiRepository implements CatalogRepository {

    private WebClient client;

    public CatalogApiRepository() {
        client = WebClient.create("https://fakestoreapi.com");
    }

    @Override
    public List<Product> getProducts() {
        return this.client
                .get()
                .uri("products")
                .retrieve()
                .bodyToFlux(FakeStoreProduct.class)
                .collect(Collectors.toList())
                .share()
                .block()
                .stream()
                .map(fakeStoreProduct -> (Product) fakeStoreProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> getCategories() {
        return this.client
                .get()
                .uri("products/categories")
                .retrieve()
                .bodyToFlux(Category.class)
                .collect(Collectors.toList())
                .share()
                .block();
    }
}
