package m2tienda.m2tienda.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import m2tienda.m2tienda.entities.Category;
import m2tienda.m2tienda.repositories.CategoryRepository;
import m2tienda.m2tienda.services.CategoryService;
import m2tienda.m2tienda.utils.DBConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CategoryServlet.class);
    private CategoryService categoryService;

    @Override
    public void init() {
        categoryService = new CategoryService(new CategoryRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("CategoryServlet - doGet() called");

        try (Connection connection = DBConnectionManager.getConnection()) {
            List<Category> categories = categoryService.getCategories(connection);

            logger.info("\n{}", String.join("\n",
                    categories.stream().map(Category::toString).toList()));

            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/WEB-INF/views/categories.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

