package com.shoppingcart.backend.controllers;

import com.google.common.collect.Iterables;
import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.requests.CategoryRequest;
import com.shoppingcart.backend.services.CategoryService;
import com.shoppingcart.backend.utils.CategoryTestUtil;
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

public class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private CategoryService categoryService;

    private CategoryTestUtil categoryTestUtil;

    private Category category;

    @BeforeEach
    public final void setUp(){
        MockitoAnnotations.initMocks(this);
        categoryTestUtil = new CategoryTestUtil();
        category = categoryTestUtil.getCategory();
    }

    private CategoryRequest getCategoryRequest(){
        return modelMapper.map(category, CategoryRequest.class);
    }

    @Test
    public final void testAll(){
        when(categoryService.all()).thenReturn(Arrays.asList(category));
        Iterable<Category> categories = categoryController.all();
        assertEquals(1, Iterables.size(categories));
    }

    @Test
    public final void testFind() throws CategoryNotFound {
        when(categoryService.find(anyLong())).thenReturn(category);
        categoryTestUtil.assertCategory(category, categoryController.find(1L));
    }

    @Test
    public final void testCategoryNotFound() throws CategoryNotFound {
        doThrow(CategoryNotFound.class).when(categoryService).find(anyLong());
        Throwable ex = assertThrows(
            CategoryNotFound.class,
            () -> categoryController.find(1L),
            "Category with id=1 not found"
        );
        assertInstanceOf(CategoryNotFound.class, ex);
    }

    @Test
    public final void testUpsert() throws CategoryNotFound {
        when(categoryService.upsert(any(Category.class))).thenReturn(category);
        categoryTestUtil.assertCategory(category, categoryController.upsert(getCategoryRequest()));
    }

    @Test
    public final void testDelete() throws CategoryNotFound {
        categoryController.delete(1L);
        verify(categoryService, times(1)).delete(1L);
    }
}
