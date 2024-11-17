package m2tienda.m2tienda.repositories;

import m2tienda.m2tienda.entities.Category;
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
        logger.info("ProductRepository.findAll()");

        String query = """
                SELECT p.id, p.categorias_id, p.nombre, p.descripcion, p.precio, p.stock,
                       p.imagen_nombre, p.imagen_tipo, p.imagen_tamanio, p.creado, p.estado,
                       c.nombre AS categorias_nombre
                FROM productos p
                INNER JOIN categorias c ON c.id = p.categorias_id
                WHERE p.estado = 1
                ORDER BY p.id
                """;

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setCategoryId(rs.getInt("categorias_id"));

                Category category = new Category();
                category.setId(rs.getInt("categorias_id"));
                category.setName(rs.getString("categorias_nombre"));
                product.setCategory(category);

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
            logger.error("DB error", e);
            throw new RepositoryException(e.getMessage());
        }
    }

    public Product findOne(Connection connection, int id) {
        logger.info("ProductRepository.findOne()");

        String query = """
                SELECT p.id, p.categorias_id, p.nombre, p.descripcion, p.precio, p.stock,
                       p.imagen_nombre, p.imagen_tipo, p.imagen_tamanio, p.creado, p.estado,
                       c.nombre AS categorias_nombre
                FROM productos p
                INNER JOIN categorias c ON c.id = p.categorias_id
                WHERE p.id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setCategoryId(rs.getInt("categorias_id"));

                    Category category = new Category();
                    category.setId(rs.getInt("categorias_id"));
                    category.setName(rs.getString("categorias_nombre"));
                    product.setCategory(category);

                    product.setName(rs.getString("nombre"));
                    product.setDescription(rs.getString("descripcion"));
                    product.setPrice(rs.getDouble("precio"));
                    product.setStock(rs.getInt("stock"));
                    product.setImage_name(rs.getString("imagen_nombre"));
                    product.setImage_type(rs.getString("imagen_tipo"));
                    product.setImage_size(rs.getLong("imagen_tamanio"));
                    product.setState(rs.getInt("estado"));

                    return product;
                }
            }

        } catch (SQLException e) {
            logger.error("DB error", e);
            throw new RepositoryException(e.getMessage());
        }

        return null;
    }

    public void create(Connection connection, Product product) {
        logger.info("ProductRepository.create()");

        String query = """
                INSERT INTO productos (
                       categorias_id, nombre, precio, stock, descripcion, estado,
                       imagen_nombre, imagen_tipo, imagen_tamanio)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStock());
            statement.setString(5, product.getDescription());
            statement.setInt(6, product.getState());

            statement.setString(7, product.getImage_name());
            statement.setString(8, product.getImage_type());
            statement.setObject(9, product.getImage_size());

            int rowsAffected = statement.executeUpdate();
            logger.info("Product created successfully, rows affected: {}", rowsAffected);

        } catch (SQLException e) {
            logger.error("DB error", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Connection connection, Product product) {
        logger.info("ProductRepository.update()");

    }

    public void delete(Connection connection, int id) throws SQLException {
        logger.info("ProductRepository.delete()");

        String sql = """
                DELETE FROM productos
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0)
                System.out.println("Product with ID " + id + " was deleted successfully.");

            else
                System.out.println("No product found with ID " + id + ".");
        }
    }
}
