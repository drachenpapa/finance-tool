package de.drachenpapa.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Toolbar {

    public static JToolBar create(JTable table) {
        JToolBar toolBar = new JToolBar();
        addButton(toolBar, table);
        addRemoveButton(toolBar, table);
        return toolBar;
    }

    private static void addButton(JToolBar toolBar, JTable table) {
        JButton addButton = new JButton("Add Row");
        addButton.addActionListener(e -> addRow(table));
        toolBar.add(addButton);
    }

    private static void addRemoveButton(JToolBar toolBar, JTable table) {
        JButton removeButton = new JButton("Remove Row");
        removeButton.addActionListener(e -> removeRow(table));
        toolBar.add(removeButton);
    }

    private static void addRow(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"New Name", 0});
    }

    private static void removeRow(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
        }
    }
}
