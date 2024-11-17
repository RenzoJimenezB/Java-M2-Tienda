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

@WebServlet("/products/create")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class ProductCreateServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ProductCreateServlet.class);
    private static final int ACTIVE = 1;

    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() {
        productService = new ProductService(new ProductRepository());
        categoryService = new CategoryService(new CategoryRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("ProductCreateServlet.doGet()");

        try (Connection connection = DBConnectionManager.getConnection()) {
            List<Category> categories = categoryService.getCategories(connection);

            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/WEB-INF/views/product/register.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("ProductCreateServlet.doPost()");

        String categoryId = req.getParameter("categorias_id");
        String name = req.getParameter("nombre");
        String price = req.getParameter("precio");
        String stock = req.getParameter("stock");
        String description = req.getParameter("descripcion");


        Product product = new Product();
        product.setCategoryId(Integer.parseInt(categoryId));
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        product.setStock(Integer.parseInt(stock));
        product.setDescription(description);
        product.setState(ACTIVE);

        Part filePart = req.getPart("imagen");
        if (filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
            File filepath = new File(getServletContext().getRealPath("") + File.separator + "files");

            if (!filepath.exists()) filepath.mkdir();
            String fileName = System.currentTimeMillis() + "-" + filePart.getSubmittedFileName();

            filePart.write(filepath + File.separator + fileName);

            logger.info("File created: {}{}{}", filepath, File.separator, fileName);

            product.setImage_name(fileName);
            product.setImage_type(filePart.getContentType());
            product.setImage_size(filePart.getSize());
        }

        logger.info(product);

        try (Connection connection = DBConnectionManager.getConnection()) {
            productService.createProduct(connection, product);

            req.getSession().setAttribute("success", "Registro guardado exitosamente");
            resp.sendRedirect(req.getContextPath() + "/products");

        } catch (Exception e) {
            logger.error(e.getStackTrace());
            throw new ServletException(e.getMessage(), e);
        }
    }
}
