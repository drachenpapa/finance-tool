package de.drachenpapa;

import de.drachenpapa.database.DatabaseConnector;
import de.drachenpapa.views.TableView;

import javax.swing.*;

public class FinanceTool {

    public static void main(String[] args) {
        DatabaseConnector.startUp();
        launchApp();
    }

    private static void launchApp() {
        SwingUtilities.invokeLater(() -> {
            TableView app = new TableView();
            app.setVisible(true);
        });
    }
}
