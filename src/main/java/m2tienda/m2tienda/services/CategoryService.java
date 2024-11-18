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
        logger.info("CategoryService.getCategories()");

        try {
            return categoryRepository.findAll(connection);

        } catch (RepositoryException e) {
            logger.error("Repository error while fetching categories: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public Category getCategory(Connection connection, int id) {
        logger.info("CategoryService.getCategory()");

        try {
            return categoryRepository.findById(connection, id);

        } catch (RepositoryException e) {
            logger.error("Repository error while fetching category by ID: {}", e.getMessage());
            return null;
        }
    }
}
