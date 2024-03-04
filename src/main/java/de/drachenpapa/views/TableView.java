package de.drachenpapa.views;

import de.drachenpapa.Messages;
import de.drachenpapa.Settings;
import de.drachenpapa.database.DatabaseConnector;
import de.drachenpapa.views.components.MenuBar;
import de.drachenpapa.views.components.Toolbar;
import de.drachenpapa.views.dialogs.DetailsDialog;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class TableView extends JFrame {

    @Getter
    private final Settings settings;
    private final ResourceBundle messages;
    private final JTable table;

    public TableView() {
        settings = new Settings();
        messages = Messages.getMessages(settings.getLocale());
        de.drachenpapa.views.components.MenuBar menuBar = new MenuBar(this);

        setTitle(messages.getString("app.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(settings.getWidth(), settings.getHeight());
        setLocation(settings.getX(), settings.getY());
        setJMenuBar(menuBar.create());

        table = new JTable();
        makeTableUneditableAndColorized();

        JScrollPane scrollPane = new JScrollPane(table);
        JToolBar toolBar = Toolbar.create(table, messages, TableView.this);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        loadTableData();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        Object[] rowData = new Object[table.getColumnCount()];
                        for (int col = 0; col < table.getColumnCount(); col++) {
                            rowData[col] = table.getValueAt(row, col);
                        }
                        DetailsDialog.show(TableView.this, table, rowData, messages);
                    }
                }
            }
        });
    }

    private void makeTableUneditableAndColorized() {
        // Date and Number seem to not work when just using Object.class
        table.setDefaultEditor(Object.class, null);
        table.setDefaultEditor(Date.class, null);
        table.setDefaultEditor(Number.class, null);

        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(messages));
        table.setDefaultRenderer(Date.class, new CustomTableCellRenderer(messages));
        table.setDefaultRenderer(Number.class, new CustomTableCellRenderer(messages));
    }

    public void loadTableData() {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM finances");
             ResultSet resultSet = statement.executeQuery()) {
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return getValueAt(0, columnIndex).getClass();
                }
            };

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i).toLowerCase();
                String translatedTitle = messages.getString("table.column." + columnName);
                model.addColumn(translatedTitle);
            }


            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }

            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading table data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void restart(JFrame currentFrame) {
        EventQueue.invokeLater(() -> {
            currentFrame.dispose();
            TableView newApp = new TableView();
            newApp.setVisible(true);
        });
    }
}
