package m2tienda.m2tienda.servlets.shop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import m2tienda.m2tienda.entities.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkout")
public class ShoppingCartServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ShoppingCartServlet.class);

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("ShoppingCartServlet.doGet()");

        try {
            HttpSession session = req.getSession();
            List<Product> shoppingCart = (List<Product>) session.getAttribute("shoppingCart");

            if (shoppingCart == null)
                shoppingCart = new ArrayList<>();

            req.setAttribute("shoppingCart", shoppingCart);
            req.getRequestDispatcher("WEB-INF/views/shop/checkout.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
