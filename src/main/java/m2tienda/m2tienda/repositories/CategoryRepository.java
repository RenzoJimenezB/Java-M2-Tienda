package m2tienda.m2tienda.repositories;

import m2tienda.m2tienda.entities.Category;
import m2tienda.m2tienda.exceptions.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private static final Logger logger = LogManager.getLogger(CategoryRepository.class);

    public List<Category> findAll(Connection connection) {
        logger.info("findAll() called");

        String query = """
                SELECT *
                FROM categorias
                ORDER BY orden
                """;

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            List<Category> categories = new ArrayList<>();

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("nombre"));
                category.setDescription(rs.getString("descripcion"));
                category.setOrder(rs.getInt("orden"));
                categories.add(category);
            }

            return categories;

        } catch (SQLException e) {
            logger.error("DB error while fetching categories", e);
            throw new RepositoryException(e.getMessage());
        }
    }
}
