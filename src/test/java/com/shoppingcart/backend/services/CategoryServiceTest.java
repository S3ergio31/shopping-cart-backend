package com.shoppingcart.backend.services;

import com.google.common.collect.Iterables;
import com.shoppingcart.backend.entities.CategoryEntity;
import com.shoppingcart.backend.exceptions.CategoryNotFound;
import com.shoppingcart.backend.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.shoppingcart.backend.domain.Category;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ModelMapper modelMapper;

    private CategoryEntity categoryEntity;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("test");
    }

    private final void assertCategory(Category category){
        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("test", category.getName());
    }

    @Test
    public final void testFind() throws CategoryNotFound {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryEntity));
        Category category = categoryService.find(1L);
        verify(categoryRepository, times(1)).findById(anyLong());
        assertCategory(category);
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
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(categoryEntity));
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
        when(categoryRepository.findByName(anyString())).thenReturn(categoryEntity);
        Category category = categoryService.findByName("test");
        verify(categoryRepository, times(1)).findByName(anyString());
        assertCategory(category);
    }

    @Test
    public final void testUpsert(){
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);
        Category category = categoryService.upsert(new Category());
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
        assertCategory(category);
    }

    @Test
    public final void testDelete(){
        categoryService.delete(1L);
        verify(categoryRepository, times(1)).deleteById(anyLong());
    }
}
