package m2tienda.m2tienda.services;

import m2tienda.m2tienda.entities.Category;
import m2tienda.m2tienda.exceptions.RepositoryException;
import m2tienda.m2tienda.repositories.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(Connection connection) {
        logger.info("CategoryService - getCategories() called");

        try {
            return categoryRepository.findAll(connection);

        } catch (RepositoryException e) {
            logger.error("Repository error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
