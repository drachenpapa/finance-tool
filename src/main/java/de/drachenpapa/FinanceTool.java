package de.drachenpapa;

import de.drachenpapa.database.DatabaseConnector;
import de.drachenpapa.database.FinancesEntry;
import de.drachenpapa.views.TableView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FinanceTool {

    public static void main(String[] args) throws SQLException {
        DatabaseConnector.startUp();
        List<FinancesEntry> finances = DatabaseConnector.getFinances();
        launchApp();
    }

    private static void launchApp() {
        SwingUtilities.invokeLater(() -> {
            TableView app = new TableView();
            app.setVisible(true);
        });
    }
}
