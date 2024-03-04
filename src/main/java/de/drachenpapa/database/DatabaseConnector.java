package de.drachenpapa.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {

    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:h2:./data/finances";
        String username = "admin";
        String password = "nimda";

        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        createTable(connection);
        preloadExampleData(connection);
        return connection;
    }

    public static void insertEntry(String date, String amount, String category, String description) {
        String sql = "INSERT INTO finances (date, amount, category, description) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date);
            statement.setString(2, amount);
            statement.setString(3, category);
            statement.setString(4, description);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateEntry(String id, String date, String amount, String category, String description) {
        String sql = "UPDATE finances SET date = ?, amount = ?, category = ?, description = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date);
            statement.setString(2, amount);
            statement.setString(3, category);
            statement.setString(4, description);
            statement.setString(5, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void removeEntry(String id) {
        String sql = "DELETE FROM finances WHERE id = ?";

        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

    private static void preloadExampleData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM finances")) {
            resultSet.next();
            int rowCount = resultSet.getInt("count");
            if (rowCount == 0) {
                insertExampleData(connection);
            }
        }
    }

    private static void insertExampleData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO finances (date, amount, category, description) VALUES " +
                    "('2024-03-01', 100.0, 'Income', 'Salary'), " +
                    "('2024-03-02', 50.0, 'Income', 'Bonus'), " +
                    "('2024-03-03', -20.0, 'Expense', 'Groceries')";
            statement.executeUpdate(sql);
        }
    }
}
