package com.shoppingcart.backend.services;

import com.google.common.collect.Iterables;
import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.entities.ProductEntity;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.exceptions.ProductNotFound;
import com.shoppingcart.backend.repositories.ProductRepository;
import com.shoppingcart.backend.utils.ProductTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

public class ProductServiceTest {
    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private ProductTestUtil productTestUtil;

    private Product product;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        productTestUtil = new ProductTestUtil();
        product = productTestUtil.getProduct();
    }

    private ProductEntity getProductEntity(){
        ProductEntity productEntity = new ModelMapper().map(product, ProductEntity.class);
        productEntity.setRate(product.getRate());
        productEntity.setCount(product.getCount());
        return productEntity;
    }

    @Test
    public final void testAll(){
        ProductEntity productEntity = getProductEntity();
        when(productRepository.findAll()).thenReturn(Arrays.asList(productEntity));
        Iterable<Product> products = productService.all();
        assertEquals(1, Iterables.size(products));
    }

    @Test
    public final void testFind() throws ProductNotFound {
        when(productRepository.findById(anyLong()))
        .thenReturn(
            Optional.of(getProductEntity())
        );
        productTestUtil.assertProduct(product, productService.find(1L));
    }

    @Test
    public final void testProductNotFound(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Throwable ex = assertThrows(
            ProductNotFound.class,
            () -> productService.find(1L)
        );
        assertEquals("Product with id=1 not found", ex.getMessage());
    }

    @Test
    public final void testExistsByTitle(){
        when(productRepository.existsByTitle(anyString())).thenReturn(true);
        assertTrue(productService.existsByTitle("test"));
    }

    @Test
    public final void testNotExistsByTitle(){
        when(productRepository.existsByTitle(anyString())).thenReturn(false);
        assertFalse(productService.existsByTitle("test"));
    }

    @Test
    public final void testUpsert() throws CategoryNotFound {
        when(productRepository.save(any(ProductEntity.class)))
            .thenReturn(getProductEntity());
        productTestUtil.assertProduct(product, productService.upsert(product));
    }

    @Test
    public final void testCannotUpsertBecauseCategoryNotFound() {
        doThrow(JpaObjectRetrievalFailureException.class)
            .when(productRepository)
            .save(any(ProductEntity.class));
        assertThrows(
            CategoryNotFound.class,
            () -> productService.upsert(product)
        );
    }

    @Test
    public final void testDelete() throws ProductNotFound {
        productService.delete(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public final void testProductNotFoundOnDelete(){
        doThrow(EmptyResultDataAccessException.class)
            .when(productRepository).deleteById(anyLong());
        assertThrows(
            ProductNotFound.class,
            () -> productService.delete(1L)
        );

    }
}
