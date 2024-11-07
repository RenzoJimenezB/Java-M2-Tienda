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
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = DBConnectionManager.getConnection();
            CategoryRepository categoryRepository = new CategoryRepository(connection);
            categoryService = new CategoryService(categoryRepository);

        } catch (SQLException e) {
            throw new ServletException("Unable to initialize CategoryServlet due to DB connection issues");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("CategoryServlet - doGet() called");

        try {
            List<Category> categories = categoryService.getCategories();

            categories.stream().forEach(category -> logger.info(category.toString()));

            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/WEB-INF/views/categories.jsp").forward(req, resp);

        } finally {
            DBConnectionManager.closeResources(connection);
        }
    }

    @Override
    public void destroy() {
        DBConnectionManager.closeResources(connection);
    }
}

