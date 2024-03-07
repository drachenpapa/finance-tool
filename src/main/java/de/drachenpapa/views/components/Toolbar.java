package de.drachenpapa.views.components;

import de.drachenpapa.database.H2Connector;
import de.drachenpapa.views.TableView;
import de.drachenpapa.views.dialogs.DetailsDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ResourceBundle;

public class Toolbar {

    public static JToolBar create(JTable table, ResourceBundle messages, TableView tableView) {
        JToolBar toolBar = new JToolBar();
        addButton(toolBar, table, messages, tableView);
        addRemoveButton(toolBar, table, messages, tableView);
        return toolBar;
    }

    private static void addButton(JToolBar toolBar, JTable table, ResourceBundle messages, TableView tableView) {
        JButton addButton = new JButton(messages.getString("toolbar.add"));
        addButton.addActionListener(e -> {
            Object[] emptyRowData = new Object[]{"", "", "", "", ""};
            DetailsDialog.show(tableView, table, emptyRowData, messages);
        });
        toolBar.add(addButton);
    }

    private static void addRemoveButton(JToolBar toolBar, JTable table, ResourceBundle messages, TableView tableView) {
        JButton removeButton = new JButton(messages.getString("toolbar.remove"));
        removeButton.addActionListener(e -> removeRow(table, tableView));
        toolBar.add(removeButton);
    }

    private static void removeRow(JTable table, TableView tableView) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String id = model.getValueAt(selectedRow, 0).toString();
            H2Connector.removeFinancesEntry(id);
            tableView.loadTableData();
        }
    }
}
