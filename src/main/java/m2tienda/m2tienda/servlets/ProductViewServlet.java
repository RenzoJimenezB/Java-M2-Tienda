package m2tienda.m2tienda.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import m2tienda.m2tienda.entities.Product;
import m2tienda.m2tienda.repositories.ProductRepository;
import m2tienda.m2tienda.services.ProductService;
import m2tienda.m2tienda.utils.DBConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/products/view")
public class ProductViewServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ProductViewServlet.class);
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService(new ProductRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("ProductViewServlet - doGet() called");

        int productId = Integer.parseInt(req.getParameter("id"));

        try (Connection connection = DBConnectionManager.getConnection()) {
            Product product = productService.getProduct(connection, productId);

            logger.info("\n{}", product);

            req.setAttribute("product", product);
            req.getRequestDispatcher("WEB-INF/views/product-view.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
