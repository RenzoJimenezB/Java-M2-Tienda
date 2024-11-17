package m2tienda.m2tienda.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import m2tienda.m2tienda.repositories.ProductRepository;
import m2tienda.m2tienda.services.ProductService;
import m2tienda.m2tienda.utils.DBConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/products/delete")
public class ProductDeleteServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ProductDeleteServlet.class);
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService(new ProductRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("ProductDeleteServlet.doGet()");

        int productId = Integer.parseInt(req.getParameter("id"));

        try (Connection connection = DBConnectionManager.getConnection()) {
            productService.deleteProduct(connection, productId);

            req.getSession().setAttribute("success", "Registro eliminado exitosamente");
            resp.sendRedirect(req.getContextPath() + "/products");

        } catch (SQLException e) {
            logger.error("Failed to delete product", e);
            throw new RuntimeException(e);
        }
    }
}

