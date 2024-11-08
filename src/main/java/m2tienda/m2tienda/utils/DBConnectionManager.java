package m2tienda.m2tienda.utils;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static final Logger logger = LogManager.getLogger(DBConnectionManager.class);
    private static final Dotenv dotenv = Dotenv.load();

    private static final String url = dotenv.get("DB_URL");
    private static final String user = dotenv.get("DB_USER");
    private static final String password = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException, ServletException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            logger.error("SQL error while getting connection.\nCode: {}\nSQLState: {}\nMessage: {}",
                    e.getErrorCode(),
                    e.getSQLState(),
                    e.getMessage());
            throw e;
        } catch (ClassNotFoundException e) {
            throw new ServletException("JDBC Driver not found", e);
        }
    }

    public static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception e) {
                logger.error("Error closing resource: {}", e.getMessage());
                System.err.println("Error closing resource: " + e.getMessage());
            }
        }
    }
}
