package m2tienda.m2tienda.servlets.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import m2tienda.m2tienda.entities.Product;
import m2tienda.m2tienda.repositories.ProductRepository;
import m2tienda.m2tienda.services.ProductService;
import m2tienda.m2tienda.utils.DBConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(PurchaseServlet.class);
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService(new ProductRepository());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("PurchaseServlet.doGet()");

        int productId = Integer.parseInt(req.getParameter("id"));

        try (Connection connection = DBConnectionManager.getConnection()) {
            Product newProduct = productService.getProduct(connection, productId);

            logger.info("Product added to shopping cart:\n{}", newProduct);

            HttpSession session = req.getSession();
            List<Product> shoppingCart = (List<Product>) session.getAttribute("shoppingCart");

            if (shoppingCart == null)
                shoppingCart = new ArrayList<>();

            shoppingCart.add(newProduct);
            session.setAttribute("shoppingCart", shoppingCart);
            session.setAttribute("success", "Producto añadido al carrito");

            resp.sendRedirect(req.getContextPath() + "/checkout");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
