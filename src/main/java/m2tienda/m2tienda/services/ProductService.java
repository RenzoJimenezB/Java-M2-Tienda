package m2tienda.m2tienda.services;

import m2tienda.m2tienda.entities.Product;
import m2tienda.m2tienda.exceptions.RepositoryException;
import m2tienda.m2tienda.repositories.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ProductService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository1) {
        this.productRepository = productRepository1;
    }

    public List<Product> getProducts(Connection connection) {
        logger.info("ProductService.getProducts()");

        try {
            return productRepository.findAll(connection);

        } catch (RepositoryException e) {
            logger.error("Repository error while fetching products: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public Product getProduct(Connection connection, int id) {
        logger.info("ProductService.getProduct()");

        try {
            return productRepository.findById(connection, id);

        } catch (RepositoryException e) {
            logger.error("Repository error while fetching product by ID: {}", e.getMessage());
            return null;
        }
    }

    public void createProduct(Connection connection, Product product) {
        logger.info("ProductService.createProduct()");

        try {
            productRepository.create(connection, product);

        } catch (RepositoryException e) {
            logger.error("Repository error while creating product: {}", e.getMessage());
        }
    }

    public boolean updateProduct(Connection connection, Product product) {
        logger.info("ProductService.updateProduct()");

        try {
            return productRepository.update(connection, product);

        } catch (RepositoryException e) {
            logger.error("Repository error while updating product: {}", e.getMessage());
        }

        return false;
    }

    public void deleteProduct(Connection connection, int id) throws SQLException {
        logger.info("ProductService.deleteProduct()");

        try {
            productRepository.delete(connection, id);

        } catch (RepositoryException e) {
            logger.error("Repository error while deleting product: {}", e.getMessage());
        }
    }
}
