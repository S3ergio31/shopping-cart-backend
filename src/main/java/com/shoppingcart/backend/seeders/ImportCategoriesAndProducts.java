package com.shoppingcart.backend.seeders;

import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.services.CatalogService;
import com.shoppingcart.backend.services.CategoryService;
import com.shoppingcart.backend.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImportCategoriesAndProducts {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired ProductService productService;

    @Bean
    public void importCategories(){
        if(!categoryService.all().iterator().hasNext()) {
            log.info("Importing categories...");
            catalogService.getCategories().stream().forEach(this::createCategory);
            log.info("Importing categories finished.");
        }
    }

    @Bean
    public void importProducts(){
        if(!productService.all().iterator().hasNext()){
            log.info("Importing products...");
            catalogService.getProducts().stream().forEach(this::createProduct);
            log.info("Importing products finished.");
        }
    }

    private void createCategory(Category category){
        if(!categoryService.existsByName(category.getName())){
            log.info("Importing category: " + category.getName());
            categoryService.upsert(category);
        }
    }

    private void createProduct(Product product){
        if(!productService.existsByTitle(product.getTitle())){
            log.info("Importing product: " + product.getTitle());
            Category category = categoryService.findByName(product.getCategory().getName());
            product.setId(null);
            product.getCategory().setId(category.getId());
            try {
                productService.upsert(product);
            } catch (CategoryNotFound e) {
                log.info(e.getMessage());
            }
        }
    }
}
