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

@WebServlet("/products/edit")
public class ProductUpdateServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ProductUpdateServlet.class);
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService(new ProductRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("ProductUpdateServlet.doGet()");

        int productId = Integer.parseInt(req.getParameter("id"));

        try (Connection connection = DBConnectionManager.getConnection()) {
            Product product = productService.getProduct(connection, productId);

            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            req.setAttribute("product", product);
            req.getRequestDispatcher("/WEB-INF/views/product/product-edit.jsp").forward(req, resp);

        } catch (SQLException e) {
            logger.error("Error retrieving product", e);
            throw new ServletException("Error retrieving product", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("ProductUpdateServlet.doPost()");

        int productId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
//        String category = req.getParameter("category");
        double price = Double.parseDouble(req.getParameter("price"));

        try (Connection connection = DBConnectionManager.getConnection()) {
            Product product = productService.getProduct(connection, productId);

            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            product.setName(name);
//            product.setCategoryName(category);
            product.setPrice(price);

//            boolean updated = productService.updateProduct(connection, product);

//            if (updated) {
//                req.getSession().setAttribute("success", "Product updated successfully!");
//                resp.sendRedirect(req.getContextPath() + "/products");
//            } else {
//                req.setAttribute("error", "Failed to update product.");
//                req.setAttribute("product", product);
//                req.getRequestDispatcher("/WEB-INF/views/product/product-edit.jsp").forward(req, resp);
//            }

        } catch (SQLException e) {
            logger.error("Error updating product", e);
            throw new ServletException("Error updating product", e);
        }
    }
}