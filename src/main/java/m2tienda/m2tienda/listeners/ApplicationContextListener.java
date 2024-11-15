package m2tienda.m2tienda.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ApplicationContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();

            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Failed to deregister JDBC driver: {}", driver, e);
            }
        }

        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
            logger.info("Abandoned connection cleanup thread shutdown successfully");
        } catch (Exception e) {
            logger.error("Failed to shutdown AbandonedConnectionCleanupThread", e);
        }

        Object dataSource = sce.getServletContext().getAttribute("dataSource");
        if (dataSource instanceof DataSource) {
            try {
                ((DataSource) dataSource).getConnection().close();
                logger.info("DataSource closed successfully");

            } catch (Exception e) {
                logger.error("Failed to close DataSource", e);
            }
        }

        logger.info("Application context cleanup completed");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Application context initialized");
    }
}
