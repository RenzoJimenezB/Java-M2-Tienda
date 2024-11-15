package m2tienda.m2tienda.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionManager {
    private static final Logger logger = LogManager.getLogger(DBConnectionManager.class);
    private static volatile DataSource dataSource;

    private static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DBConnectionManager.class) {
                if (dataSource == null) {
                    try {
                        Context ctx = new InitialContext();
                        dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/tienda");
                        logger.info("DataSource initialized successfully");

                    } catch (NamingException e) {
                        logger.error("Failed to locate the DataSource for jdbc/tienda: {}", e.getMessage());
                        throw new RuntimeException("Could not initialize DataSource", e);
                    }
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {

        try {
            Connection connection = getDataSource().getConnection();
            logger.info("Successfully obtained a connection from the pool");
            return connection;
            
        } catch (SQLException e) {
            logger.error("SQL error while getting connection.\nCode: {}\nSQLState: {}\nMessage: {}",
                    e.getErrorCode(),
                    e.getSQLState(),
                    e.getMessage());
            throw e;
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
