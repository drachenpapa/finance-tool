package de.drachenpapa.database;

import de.drachenpapa.database.converter.CategoryConverter;
import de.drachenpapa.database.records.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDB {

    static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS categories (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "category VARCHAR(255) NOT NULL, " +
                "income BOOLEAN NOT NULL)";
        H2Connector.executeUpdate(sql);
    }

    public static List<Category> get() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection connection = H2Connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            categories = CategoryConverter.convert(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return categories;
    }

    public static void insert(String name, String category, String categoryType) {
        String sql = "INSERT INTO transactions (name, category, categoryType) VALUES (?, ?, ?, ?)";
        H2Connector.executeUpdate(sql, name, category, categoryType);
    }

    public static void update(String id, String name, String category, String categoryType) {
        String sql = "UPDATE transactions SET name = ?, category = ?, categoryType = ? WHERE id = ?";
        H2Connector.executeUpdate(sql, name, category, categoryType, id);
    }

    public static void remove(String id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        H2Connector.executeUpdate(sql, id);
    }
}
