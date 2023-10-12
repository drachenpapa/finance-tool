package de.drachenpapa.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:h2:./data/finances";
        String username = "admin";
        String password = "nimda";

        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        return connection;
    }
}
