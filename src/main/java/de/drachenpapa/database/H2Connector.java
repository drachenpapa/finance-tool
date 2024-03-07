package de.drachenpapa.database;

import de.drachenpapa.database.converter.AccountConverter;
import de.drachenpapa.database.converter.CategoryConverter;
import de.drachenpapa.database.converter.TransactionConverter;
import de.drachenpapa.database.records.Account;
import de.drachenpapa.database.records.Category;
import de.drachenpapa.database.records.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2Connector {

    private static final String JDBC_URL = "jdbc:h2:./data/finances";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "nimda";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    private static void executeUpdate(String sql, Object... params) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void createTables() {
        createAccountsTable();
        createCategoriesTable();
        createTransactionsTable();
    }

    private static void createAccountsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS accounts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "iban VARCHAR(22) NOT NULL, " +
                "startBudget DECIMAL(10, 2) NOT NULL, " +
                "budget DECIMAL(10, 2) NOT NULL)";
        executeUpdate(sql);
    }

    private static void createCategoriesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS categories (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "category VARCHAR(255) NOT NULL, " +
                "income BOOLEAN NOT NULL)";
        executeUpdate(sql);
    }

    private static void createTransactionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "date DATE NOT NULL, " +
                "amount DECIMAL(10, 2) NOT NULL, " +
                "description VARCHAR(255) NOT NULL, " +
                "account_id INT NOT NULL, " +
                "category_id INT NOT NULL, " +
                "FOREIGN KEY (account_id) REFERENCES accounts(id), " +
                "FOREIGN KEY (category_id) REFERENCES categories(id))";
        executeUpdate(sql);
    }

    private static boolean isTableEmpty(String tableName) {
        String sql = "SELECT COUNT(*) AS count FROM " + tableName;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            int rowCount = resultSet.getInt("count");
            return rowCount == 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void preloadExampleData() {
        if (isTableEmpty("transactions")) {
            insertExampleData();
        }
    }

    private static void insertExampleData() {
        String[] accountsData = {
                "('Main Account', 'DE12345678901234567890', '0', '0')"
        };

        for (String data : accountsData) {
            String sql = "INSERT INTO accounts (name, iban, startBudget, budget) VALUES " + data;
            executeUpdate(sql);
        }

        String[] categoriesData = {
                "('Income', 'Salary', true)",
                "('Expense', 'Groceries', false)"
        };

        for (String data : categoriesData) {
            String sql = "INSERT INTO categories (name, category, income) VALUES " + data;
            executeUpdate(sql);
        }

        String[] transactionsData = {
                "('2024-03-01', 100.0, 'Salary', 1, 1)",
                "('2024-03-02', 50.0, 'Bonus', 1, 1)",
                "('2024-03-03', -20.0, 'Groceries', 1, 2)"
        };

        for (String data : transactionsData) {
            String sql = "INSERT INTO transactions (date, amount, description, account_id, category_id) VALUES " + data;
            executeUpdate(sql);
        }
    }


   public static void startUp() {
       createTables();
       preloadExampleData();
   }

    public static List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            transactions = TransactionConverter.convert(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return transactions;
    }

    public static List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            accounts = AccountConverter.convert(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return accounts;
    }

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            categories = CategoryConverter.convert(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return categories;
    }

    public static void insertTransaction(String date, String amount, String category, String description) {
        String sql = "INSERT INTO transactions (date, amount, category, description) VALUES (?, ?, ?, ?)";
        executeUpdate(sql, date, amount, category, description);
    }

    public static void updateTransaction(String id, String date, String amount, String category, String description) {
        String sql = "UPDATE transactions SET date = ?, amount = ?, category = ?, description = ? WHERE id = ?";
        executeUpdate(sql, date, amount, category, description, id);
    }

    public static void removeTransaction(String id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        executeUpdate(sql, id);
    }

    // TODO insert/update/remove account/category
}
