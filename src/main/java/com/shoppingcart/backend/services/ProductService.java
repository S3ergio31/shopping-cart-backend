package com.shoppingcart.backend.services;

import com.google.common.reflect.TypeToken;
import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.domain.Rating;
import com.shoppingcart.backend.entities.ProductEntity;
import com.shoppingcart.backend.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    public Iterable<Product> all(){
        Iterable<ProductEntity> products = productRepository.findAll();
        Type listType = new TypeToken<List<ProductEntity>>() {}.getType();
        return modelMapper.map(products, listType);
    }
    public Product find(Long id){
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        Product product = modelMapper.map(productEntity, Product.class);
        Rating rating = new Rating();
        rating.setCount(productEntity.get().getCount());
        rating.setRate(productEntity.get().getRate());
        product.setRating(rating);
        return product;
    }
    public boolean existsByTitle(String title){
        return productRepository.existsByTitle(title);
    }
    public Product upsert(Product product) {
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        productEntity.setCount(product.getRating().getCount());
        productEntity.setRate(product.getRating().getRate());
        ProductEntity createdProductEntity = productRepository.save(productEntity);
        return modelMapper.map(createdProductEntity, Product.class);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
