package de.drachenpapa.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Toolbar {

    public static JToolBar create(JTable table) {
        JToolBar toolBar = new JToolBar();
        JButton addButton = new JButton("Add Row");
        JButton removeButton = new JButton("Remove Row");

        addButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{"New Name", 0});
        });

        removeButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
            }
        });

        toolBar.add(addButton);
        toolBar.add(removeButton);

        return toolBar;
    }
}
