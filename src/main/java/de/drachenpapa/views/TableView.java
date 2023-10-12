package de.drachenpapa.views;

import de.drachenpapa.Settings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableView extends JFrame {

    private final Settings settings;

    public TableView() {
        settings = new Settings();

        setTitle("Finanzplaner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(settings.getWidth(), settings.getHeight());
        setLocation(settings.getX(), settings.getY());

        DefaultTableModel defaultTableModel = prefillTable();
        JTable table = new JTable(defaultTableModel);

        table.setDefaultEditor(Object.class, null);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        Object[] rowData = new Object[defaultTableModel.getColumnCount()];
                        for (int col = 0; col < defaultTableModel.getColumnCount(); col++) {
                            rowData[col] = table.getValueAt(selectedRow, col);
                        }
                        // Create a new window to display row information
                        RowInfo.show(table, rowData);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        JToolBar toolBar = Toolbar.create(table);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Rectangle bounds = getBounds();
                settings.saveSettings(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });
    }

    private DefaultTableModel prefillTable() {
        String[] columnNames = {"Name", "Age"};
        Object[][] rowData = {
                {"Alice", 30},
                {"Bob", 25},
                {"Charlie", 35}
        };
        return new DefaultTableModel(rowData, columnNames);
    }
}
