package m2tienda.m2tienda.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import m2tienda.m2tienda.repositories.ProductRepository;
import m2tienda.m2tienda.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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
        logger.info("ProductUpdateServlet - doGet() called");

    }
}
