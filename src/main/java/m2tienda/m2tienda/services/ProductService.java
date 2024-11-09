package m2tienda.m2tienda.services;

import m2tienda.m2tienda.entities.Product;
import m2tienda.m2tienda.exceptions.RepositoryException;
import m2tienda.m2tienda.repositories.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class ProductService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository1) {
        this.productRepository = productRepository1;
    }

    public List<Product> getProducts(Connection connection) {
        logger.info("getProducts() called");

        try {
            return productRepository.findAll(connection);

        } catch (RepositoryException e) {
            logger.error("Repository error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
