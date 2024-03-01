package de.drachenpapa;

import de.drachenpapa.database.DatabaseConnector;
import de.drachenpapa.views.TableView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class FinanceTool {

    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        launchApp();
    }

    private static void launchApp() {
        SwingUtilities.invokeLater(() -> {
            TableView app = new TableView();
            app.setVisible(true);
        });
    }
}
