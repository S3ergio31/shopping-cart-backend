package com.shoppingcart.backend.services;

import com.google.common.reflect.TypeToken;
import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.domain.Rating;
import com.shoppingcart.backend.entities.ProductEntity;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.exceptions.ProductNotFound;
import com.shoppingcart.backend.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.util.List;

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
    public Product find(Long id) throws ProductNotFound {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ProductNotFound(id));
        Product product = modelMapper.map(productEntity, Product.class);
        Rating rating = new Rating();
        rating.setCount(productEntity.getCount());
        rating.setRate(productEntity.getRate());
        product.setRating(rating);
        return product;
    }
    public boolean existsByTitle(String title){
        return productRepository.existsByTitle(title);
    }
    public Product upsert(Product product) throws CategoryNotFound {
        try {
            ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
            productEntity.setCount(product.getRating().getCount());
            productEntity.setRate(product.getRating().getRate());
            ProductEntity createdProductEntity = productRepository.save(productEntity);
            Product createdProduct = modelMapper.map(createdProductEntity, Product.class);
            Rating rating = new Rating();
            rating.setCount(createdProductEntity.getCount());
            rating.setRate(createdProductEntity.getRate());
            createdProduct.setRating(rating);
            return createdProduct;
        }
        catch(JpaObjectRetrievalFailureException ex){
            throw new CategoryNotFound(product.getCategory().getId());
        }
    }

    public void delete(Long id) throws ProductNotFound {
        try {
            productRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException ex){
            throw new ProductNotFound(id);
        }
    }
}
