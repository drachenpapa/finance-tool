package de.drachenpapa.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:h2:./data/finances";
        String username = "admin";
        String password = "nimda";

        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        createTable(connection);
        return connection;
    }

    private static void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS finances (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "date DATE NOT NULL, " +
                    "amount DECIMAL(10, 2) NOT NULL, " +
                    "category VARCHAR(255) NOT NULL, " +
                    "description VARCHAR(255) NOT NULL)";
            statement.executeUpdate(sql);
        }
    }
}
