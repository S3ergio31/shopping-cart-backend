package com.shoppingcart.backend.controllers;

import com.google.common.collect.Iterables;
import com.shoppingcart.backend.domain.Product;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.exceptions.ProductNotFound;
import com.shoppingcart.backend.requests.ProductRequest;
import com.shoppingcart.backend.services.ProductService;
import com.shoppingcart.backend.utils.ProductTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private ProductService productService;

    private ProductTestUtil productTestUtil;

    private Product product;

    @BeforeEach
    public final void setUp(){
        MockitoAnnotations.initMocks(this);
        productTestUtil = new ProductTestUtil();
        product = productTestUtil.getProduct();
    }

    private ProductRequest getProductRequest(){
        return modelMapper.map(product, ProductRequest.class);
    }

    @Test
    public final void testAll(){
        when(productService.all()).thenReturn(Arrays.asList(product));
        Iterable<Product> products = productController.all();
        assertEquals(1, Iterables.size(products));
    }

    @Test
    public final void testFind() throws ProductNotFound {
        when(productService.find(anyLong())).thenReturn(product);
        productTestUtil.assertProduct(product, productController.find(1L));
    }

    @Test
    public final void testProductNotFound() throws ProductNotFound {
        doThrow(ProductNotFound.class).when(productService).find(anyLong());
        Throwable ex = assertThrows(
            ProductNotFound.class,
            () -> productController.find(1L),
            "Product with id=1 not found"
        );
        assertInstanceOf(ProductNotFound.class, ex);
    }

    @Test
    public final void testUpsert() throws CategoryNotFound {
        when(productService.upsert(any(Product.class))).thenReturn(product);
        productTestUtil.assertProduct(product, productController.upsert(getProductRequest()));
    }

    @Test
    public final void testCannotUpsertBecauseCategoryNotFound() throws CategoryNotFound {
        doThrow(CategoryNotFound.class).when(productService).upsert(any(Product.class));
        assertThrows(
            CategoryNotFound.class,
            () -> productController.upsert(getProductRequest())
        );
    }

    @Test
    public final void testDelete() throws ProductNotFound {
        productController.delete(1L);
        verify(productService, times(1)).delete(1L);
    }

    @Test
    public final void testProductNotFoundOnDelete() throws ProductNotFound {
        doThrow(ProductNotFound.class)
            .when(productService).delete(anyLong());
        assertThrows(
            ProductNotFound.class,
            () -> productController.delete(1L)
        );

    }
}
