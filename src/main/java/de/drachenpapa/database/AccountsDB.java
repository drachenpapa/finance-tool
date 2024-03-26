package de.drachenpapa.database;

import de.drachenpapa.database.converter.AccountConverter;
import de.drachenpapa.database.records.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static de.drachenpapa.database.H2Connector.executeUpdate;

public class AccountsDB {

    static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS accounts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "iban VARCHAR(22) NOT NULL, " +
                "startBudget DECIMAL(10, 2) NOT NULL, " +
                "budget DECIMAL(10, 2) NOT NULL)";
        executeUpdate(sql);
    }

    public static List<Account> get() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";

        try (Connection connection = H2Connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            accounts = AccountConverter.convert(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return accounts;
    }
    public static void insert(String name, String iban, double startBudget, double budget) {
        String sql = "INSERT INTO accounts (name, iban, startBudget, budget) VALUES (?, ?, ?, ?)";
        H2Connector.executeUpdate(sql, name, iban, startBudget, budget);
    }

    public static void update(String id, String name, String iban, double startBudget, double budget) {
        String sql = "UPDATE accounts SET name = ?, iban = ?, startBudget = ?, budget = ? WHERE id = ?";
        H2Connector.executeUpdate(sql, name, iban, startBudget, budget, id);
    }

    public static void remove(String id) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        H2Connector.executeUpdate(sql, id);
    }
}
