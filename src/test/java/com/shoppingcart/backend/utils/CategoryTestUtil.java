package com.shoppingcart.backend.utils;

import com.shoppingcart.backend.domain.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryTestUtil {
    public Category getCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("test");
        return category;
    }

    public final void assertCategory(Category expected, Category current){
        assertNotNull(current);
        assertEquals(expected.getId(), current.getId());
        assertEquals(expected.getName(), current.getName());
    }
}
