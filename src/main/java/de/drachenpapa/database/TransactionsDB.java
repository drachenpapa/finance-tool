package de.drachenpapa.database;

import de.drachenpapa.database.converter.TransactionConverter;
import de.drachenpapa.database.records.Transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransactionsDB {

    static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "date DATE NOT NULL, " +
                "amount DECIMAL(10, 2) NOT NULL, " +
                "description VARCHAR(255) NOT NULL, " +
                "account_id INT NOT NULL, " +
                "category_id INT NOT NULL, " +
                "FOREIGN KEY (account_id) REFERENCES accounts(id), " +
                "FOREIGN KEY (category_id) REFERENCES categories(id))";
        H2Connector.executeUpdate(sql);
    }

    public static List<Transaction> get() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection connection = H2Connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            transactions = TransactionConverter.convert(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return transactions;
    }

    public static void insert(String date, String amount, String category, String description) {
        String sql = "INSERT INTO transactions (date, amount, category, description) VALUES (?, ?, ?, ?)";
        H2Connector.executeUpdate(sql, date, amount, category, description);
    }

    public static void update(String id, String date, String amount, String category, String description) {
        String sql = "UPDATE transactions SET date = ?, amount = ?, category = ?, description = ? WHERE id = ?";
        H2Connector.executeUpdate(sql, date, amount, category, description, id);
    }

    public static void remove(String id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        H2Connector.executeUpdate(sql, id);
    }
}
