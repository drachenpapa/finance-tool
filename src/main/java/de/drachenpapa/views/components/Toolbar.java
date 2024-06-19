package de.drachenpapa.views.components;

import de.drachenpapa.database.TransactionsDB;
import de.drachenpapa.views.TableView;
import de.drachenpapa.views.dialogs.NewTransactionDialog;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ResourceBundle;

public class Toolbar {

    public static JToolBar create(JTable table, ResourceBundle messages, TableView tableView) {
        JToolBar toolBar = new JToolBar();

        addNewTransactionButton(toolBar, table, messages, tableView);
        addRemoveTransactionButton(toolBar, table, messages, tableView);
        toolBar.addSeparator();

        addNewPeriodicTransactionButton(toolBar, table, messages, tableView);
        toolBar.addSeparator();

        addFromDatePicker();
        addToDatePicker();
        toolBar.addSeparator();

        addStatisticsButton(toolBar);

        return toolBar;
    }

    private static void addNewTransactionButton(JToolBar toolBar, JTable table, ResourceBundle messages, TableView tableView) {
        JButton newTransactionButton = new JButton(messages.getString("toolbar.add")); // new JButton(new ImageIcon(Toolbar.class.getResource("/icons/plus.png")));
        newTransactionButton.addActionListener(e -> NewTransactionDialog.show(tableView, table, messages));
        toolBar.add(newTransactionButton);
    }

    private static void addRemoveTransactionButton(JToolBar toolBar, JTable table, ResourceBundle messages, TableView tableView) {
        JButton removeTransactionButton = new JButton(messages.getString("toolbar.remove"));
        removeTransactionButton.addActionListener(e -> removeTransaction(table, tableView));
        toolBar.add(removeTransactionButton);
    }

    private static void addNewPeriodicTransactionButton(JToolBar toolBar, JTable table, ResourceBundle messages, TableView tableView) {
        JButton newPeriodicTransactionButton = new JButton(messages.getString("toolbar.add"));
        newPeriodicTransactionButton.setToolTipText("Dummy Button");
        newPeriodicTransactionButton.addActionListener(e -> {
        });
        toolBar.add(newPeriodicTransactionButton);
    }

    private static void addFromDatePicker() {
        JXDatePicker fromDatePicker = new JXDatePicker();
        fromDatePicker.setToolTipText("Dummy Button");
    }

    private static void addToDatePicker() {
        JXDatePicker toDatePicker = new JXDatePicker();
        toDatePicker.setToolTipText("Dummy Button");
    }

    private static void addStatisticsButton(JToolBar toolBar) {
        JButton statisticsButton = new JButton("Statistics");
        toolBar.add(statisticsButton);
    }

    private static void removeTransaction(JTable table, TableView tableView) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String id = model.getValueAt(selectedRow, 0).toString();
            TransactionsDB.remove(id);
            tableView.loadTableData();
        }
    }
}
