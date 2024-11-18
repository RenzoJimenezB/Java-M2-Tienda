package m2tienda.m2tienda.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import m2tienda.m2tienda.entities.Category;
import m2tienda.m2tienda.entities.Product;
import m2tienda.m2tienda.repositories.CategoryRepository;
import m2tienda.m2tienda.repositories.ProductRepository;
import m2tienda.m2tienda.services.CategoryService;
import m2tienda.m2tienda.services.ProductService;
import m2tienda.m2tienda.utils.DBConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class ProductUpdateServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ProductUpdateServlet.class);

    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() {
        productService = new ProductService(new ProductRepository());
        categoryService = new CategoryService(new CategoryRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("ProductUpdateServlet.doGet()");

        int productId = Integer.parseInt(req.getParameter("id"));

        try (Connection connection = DBConnectionManager.getConnection()) {
            Product product = productService.getProduct(connection, productId);
            List<Category> categories = categoryService.getCategories(connection);

            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            req.setAttribute("product", product);
            req.setAttribute("categories", categories);
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

        String categoryId = req.getParameter("categoryId");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String stock = req.getParameter("stock");
        String description = req.getParameter("description");

        try (Connection connection = DBConnectionManager.getConnection()) {
            Product product = productService.getProduct(connection, productId);

            if (product == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                        String.format("Product with ID %d not found", productId));
                return;
            }

            product.setCategoryId(Integer.parseInt(categoryId));
            product.setName(name);
            product.setPrice(Double.parseDouble(price));
            product.setStock(Integer.parseInt(stock));
            product.setDescription(description);

            Part filePart = req.getPart("image");
            if (filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
                File filepath = new File(getServletContext().getRealPath("") + File.separator + "files");

                if (!filepath.exists()) filepath.mkdir();
                String fileName = System.currentTimeMillis() + "-" + filePart.getSubmittedFileName();

                filePart.write(filepath + File.separator + fileName);

                logger.info("File created: {}{}{}", filepath, File.separator, fileName);

                product.setImage_name(fileName);
                product.setImage_type(filePart.getContentType());
                product.setImage_size(filePart.getSize());
                
            } else {
                product.setImage_name(null);
                product.setImage_type(null);
                product.setImage_size(null);
            }

            boolean updated = productService.updateProduct(connection, product);

            if (updated) {
                req.getSession().setAttribute("success", "Registro actualizado exitosamente");
                resp.sendRedirect(req.getContextPath() + "/products");

            } else {
                req.setAttribute("error", "Failed to update product");
                req.setAttribute("product", product);
                req.getRequestDispatcher("/WEB-INF/views/product/product-edit.jsp").forward(req, resp);
            }

        } catch (SQLException e) {
            logger.error("Error updating product", e);
            throw new ServletException("Error updating product", e);
        }
    }
}