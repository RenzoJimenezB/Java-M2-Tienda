package m2tienda.m2tienda.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String url = dotenv.get("DB_URL");
    private static final String user = dotenv.get("DB_USER");
    private static final String password = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        try {
            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.out.println("SQL error code: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("SQL error message: " + e.getMessage());
        }
        return null;
    }

    public static void closeConnection(Connection connection) {
    }
}
