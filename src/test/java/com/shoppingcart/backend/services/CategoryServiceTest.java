package com.shoppingcart.backend.services;

import com.google.common.collect.Iterables;
import com.shoppingcart.backend.domain.Category;
import com.shoppingcart.backend.entities.CategoryEntity;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.repositories.CategoryRepository;
import com.shoppingcart.backend.utils.CategoryTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ModelMapper modelMapper;

    private Category category;

    private CategoryTestUtil categoryTestUtil;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryTestUtil = new CategoryTestUtil();
        category = categoryTestUtil.getCategory();
    }

    private CategoryEntity getCategoryEntity(){
        return new ModelMapper().map(category, CategoryEntity.class);
    }

    @Test
    public final void testFind() throws CategoryNotFound {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(getCategoryEntity()));
        Category foundCategory = categoryService.find(1L);
        verify(categoryRepository, times(1)).findById(anyLong());
        categoryTestUtil.assertCategory(category, foundCategory);
    }

    @Test
    public void testCategoryNotFound(){
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Throwable exception = assertThrows(
                CategoryNotFound.class,
                () ->  categoryService.find(1L)
        );
        verify(categoryRepository, times(1)).findById(anyLong());
        assertEquals("Category with id=1 not found", exception.getMessage());
    }

    @Test
    public final void testGetAllCategories(){
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(getCategoryEntity()));
        Iterable<Category> categories = categoryService.all();
        verify(categoryRepository, times(1)).findAll();
        assertEquals(1, Iterables.size(categories));
    }

    @Test
    public final void testGetAllCategoriesIsEmpty(){
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());
        Iterable<Category> categories = categoryService.all();
        verify(categoryRepository, times(1)).findAll();
        assertEquals(0, Iterables.size(categories));
    }

    @Test
    public final void testExistsByName(){
        when(categoryRepository.existsByName(anyString())).thenReturn(true);
        assertTrue(categoryService.existsByName("test"));
        verify(categoryRepository, times(1)).existsByName(anyString());
    }

    @Test
    public final void testNotExistsByName(){
        when(categoryRepository.existsByName(anyString())).thenReturn(false);
        assertFalse(categoryService.existsByName("test"));
        verify(categoryRepository, times(1)).existsByName(anyString());
    }

    @Test
    public final void testFindByName(){
        when(categoryRepository.findByName(anyString())).thenReturn(getCategoryEntity());
        Category foundCategory = categoryService.findByName("test");
        verify(categoryRepository, times(1)).findByName(anyString());
        categoryTestUtil.assertCategory(category, foundCategory);
    }

    @Test
    public final void testUpsert(){
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(getCategoryEntity());
        Category foundCategory = categoryService.upsert(new Category());
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
        categoryTestUtil.assertCategory(category, foundCategory);
    }

    @Test
    public final void testDelete(){
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(getCategoryEntity()));
        doNothing().when(categoryRepository).delete(any(CategoryEntity.class));
        try {
            categoryService.delete(1L);
        } catch (CategoryNotFound e) {
            throw new RuntimeException(e);
        }
        verify(categoryRepository, times(1)).delete(any(CategoryEntity.class));
    }

    @Test
    public void testCategoryNotFoundOnDelete(){
        Throwable exception = assertThrows(
            CategoryNotFound.class,
            () ->  categoryService.delete(1L)
        );
        assertEquals("Category with id=1 not found", exception.getMessage());
    }

}
