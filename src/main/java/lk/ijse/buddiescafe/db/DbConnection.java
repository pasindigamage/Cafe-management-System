package lk.ijse.buddiescafe.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private Connection connection;

    private DbConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/buddiescafe",
                    "root",
                    "Ijse@123"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish a database connection", e);
        }
    }

    public static DbConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/buddiescafe",
                        "root",
                        "Ijse@123"
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get database connection", e);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database connection", e);
        }
    }
}
