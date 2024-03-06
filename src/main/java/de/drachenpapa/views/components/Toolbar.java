package de.drachenpapa.views.components;

import de.drachenpapa.database.DatabaseConnector;
import de.drachenpapa.views.TableView;
import de.drachenpapa.views.dialogs.DetailsDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ResourceBundle;

public class Toolbar {

    public static JToolBar create(JTable table, ResourceBundle messages, TableView tableView) {
        JToolBar toolBar = new JToolBar();
        addButton(toolBar, table, messages, tableView);
        addRemoveButton(toolBar, table, tableView);
        return toolBar;
    }

    private static void addButton(JToolBar toolBar, JTable table, ResourceBundle messages, TableView tableView) {
        JButton addButton = new JButton("Add Row");
        addButton.addActionListener(e -> {
            Object[] emptyRowData = new Object[]{"", "", "", "", ""};
            DetailsDialog.show(tableView, table, emptyRowData, messages);
        });
        toolBar.add(addButton);
    }

    private static void addRemoveButton(JToolBar toolBar, JTable table, TableView tableView) {
        JButton removeButton = new JButton("Remove Row");
        removeButton.addActionListener(e -> removeRow(table, tableView));
        toolBar.add(removeButton);
    }

    private static void removeRow(JTable table, TableView tableView) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String id = model.getValueAt(selectedRow, 0).toString();
            DatabaseConnector.removeFinancesEntry(id);
            tableView.loadTableData();
        }
    }
}
