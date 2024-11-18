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

        String sql = """
                SELECT p.id, p.categorias_id, p.nombre, p.descripcion, p.precio, p.stock,
                       p.imagen_nombre, p.imagen_tipo, p.imagen_tamanio, p.creado, p.estado,
                       c.nombre AS categorias_nombre
                FROM productos p
                INNER JOIN categorias c ON c.id = p.categorias_id
                WHERE p.estado = 1
                ORDER BY p.id
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
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

    public Product findById(Connection connection, int id) {
        logger.info("ProductRepository.findOne()");

        String sql = """
                SELECT p.id, p.categorias_id, p.nombre, p.descripcion, p.precio, p.stock,
                       p.imagen_nombre, p.imagen_tipo, p.imagen_tamanio, p.creado, p.estado,
                       c.nombre AS categorias_nombre
                FROM productos p
                INNER JOIN categorias c ON c.id = p.categorias_id
                WHERE p.id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

        String sql = """
                INSERT INTO productos (
                       categorias_id, nombre, precio, stock, descripcion, estado,
                       imagen_nombre, imagen_tipo, imagen_tamanio)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setString(5, product.getDescription());
            ps.setInt(6, product.getState());

            ps.setString(7, product.getImage_name());
            ps.setString(8, product.getImage_type());
            ps.setObject(9, product.getImage_size());

            int rowsAffected = ps.executeUpdate();
            logger.info("Product created successfully, rows affected: {}", rowsAffected);

        } catch (SQLException e) {
            logger.error("DB error", e);
            throw new RepositoryException(e.getMessage());
        }
    }

    public boolean update(Connection connection, Product product) {
        logger.info("ProductRepository.update()");

        StringBuilder sql = new StringBuilder("UPDATE productos SET ");

        List<String> fields = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        if (product.getName() != null) {
            fields.add("nombre=?");
            values.add(product.getName());
        }

        if (product.getCategory() != null) {
            fields.add("categorias_id=?");
            values.add(product.getCategory().getId());
        }

        if (product.getPrice() != null) {
            fields.add("precio=?");
            values.add(product.getPrice());
        }

        if (product.getStock() != null) {
            fields.add("stock=?");
            values.add(product.getStock());
        }

        if (product.getDescription() != null) {
            fields.add("descripcion=?");
            values.add(product.getDescription());
        }

        if (product.getState() != null) {
            fields.add("estado=?");
            values.add(product.getState());
        }

        if (product.getImage_name() != null) {
            fields.add("imagen_nombre=?");
            values.add(product.getImage_name());
        }

        if (product.getImage_type() != null) {
            fields.add("imagen_tipo=?");
            values.add(product.getImage_type());
        }

        if (product.getImage_size() != null) {
            fields.add("imagen_tamanio=?");
            values.add(product.getImage_size());
        }

        if (fields.isEmpty())
            throw new IllegalArgumentException(String.format("No fields to update for product with ID %d", product.getId()));

        sql.append(String.join(", ", fields));
        sql.append(" WHERE id=?");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : values)
                ps.setObject(index++, value);

            ps.setInt(index, product.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product with ID {} was updated successfully", product.getId());
                return true;
            }

        } catch (SQLException e) {
            logger.error("DB error", e);
            throw new RepositoryException(e.getMessage());
        }

        return false;
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
                logger.info("Product with ID {} was deleted successfully", rowsAffected);

            else
                logger.info("No product found with ID {}", rowsAffected);
        }
    }
}
