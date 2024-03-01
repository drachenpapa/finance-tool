package de.drachenpapa.views;

import de.drachenpapa.Messages;
import de.drachenpapa.Settings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

public class TableView extends JFrame {

    private final Settings settings;
    private final ResourceBundle messages;

    public TableView() {
        settings = new Settings();
        messages = Messages.getMessages(settings.getLocale());
        MenuBar menuBar = new MenuBar(this);

        setTitle(messages.getString("app.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(settings.getWidth(), settings.getHeight());
        setLocation(settings.getX(), settings.getY());
        setJMenuBar(menuBar.create());

        DefaultTableModel defaultTableModel = prefillTable();
        JTable table = new JTable(defaultTableModel);
        configureTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        JToolBar toolBar = Toolbar.create(table);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveSettings();
            }
        });
    }

    private DefaultTableModel prefillTable() {
        String[] columnNames = {
                messages.getString("table.column.name"),
                messages.getString("table.column.age")};
        Object[][] rowData = {
                {"Alice", 30},
                {"Bob", 25},
                {"Charlie", 35}
        };
        return new DefaultTableModel(rowData, columnNames);
    }

    private void configureTable(JTable table) {
        table.setDefaultEditor(Object.class, null);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        Object[] rowData = new Object[table.getColumnCount()];
                        for (int col = 0; col < table.getColumnCount(); col++) {
                            rowData[col] = table.getValueAt(selectedRow, col);
                        }
                        RowInfo.show(table, rowData);
                    }
                }
            }
        });
    }

    private void saveSettings() {
        Rectangle bounds = getBounds();
        settings.saveSettings(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public static void restart(JFrame currentFrame) {
        EventQueue.invokeLater(() -> {
            currentFrame.dispose();
            TableView newApp = new TableView();
            newApp.setVisible(true);
        });
    }
}
