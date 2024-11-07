package m2tienda.m2tienda.services;

import m2tienda.m2tienda.entities.Category;
import m2tienda.m2tienda.repositories.CategoryRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        try {
            return categoryRepository.findAll();
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
}
