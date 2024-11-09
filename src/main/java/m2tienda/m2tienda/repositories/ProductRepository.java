package m2tienda.m2tienda.repositories;

import m2tienda.m2tienda.entities.Product;
import m2tienda.m2tienda.exceptions.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final Logger logger = LogManager.getLogger(ProductRepository.class);

    public List<Product> findAll(Connection connection) {
        logger.info("findAll() called");

        String query = """
                SELECT *
                FROM productos
                """;

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setCategoryId(rs.getInt("categorias_id"));
                product.setName(rs.getString("nombre"));
                product.setDescription(rs.getString("descripcion"));
                product.setPrice(rs.getDouble("precio"));
                product.setStock(rs.getInt("stock"));
                product.setImage_name(rs.getString("imagen_nombre"));
                product.setImage_type(rs.getString("imagen_tipo"));
                product.setImage_size(rs.getLong("imagen_tamanio"));
                product.setState(rs.getInt("estado"));
                products.add(product);
            }

            return products;

        } catch (SQLException e) {
            logger.error("DB error while fetching products", e);
            throw new RepositoryException(e.getMessage());
        }
    }
}
