package de.drachenpapa.views;

import de.drachenpapa.Messages;
import de.drachenpapa.Settings;
import de.drachenpapa.database.DatabaseConnector;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ResourceBundle;

public class TableView extends JFrame {

    @Getter
    private final Settings settings;
    private final ResourceBundle messages;
    private final JTable table;

    public TableView() {
        settings = new Settings();
        messages = Messages.getMessages(settings.getLocale());
        MenuBar menuBar = new MenuBar(this);

        setTitle(messages.getString("app.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(settings.getWidth(), settings.getHeight());
        setLocation(settings.getX(), settings.getY());
        setJMenuBar(menuBar.create());

        table = new JTable();
        table.setDefaultRenderer(Object.class, new CustomCellRenderer());
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        JToolBar toolBar = Toolbar.create(table);
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
                        RowInfo.show(table, rowData, messages);
                    }
                }
            }
        });
    }

    private void loadTableData() {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT date, amount, category, description FROM finances");
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
                if (!columnName.equals("index")) {
                    String translatedTitle = messages.getString("table.column." + columnName);
                    model.addColumn(translatedTitle);
                }
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount - 1];
                for (int i = 1; i <= columnCount; i++) {
                    if (!metaData.getColumnName(i).equalsIgnoreCase("index")) {
                        rowData[i - 1] = resultSet.getObject(i);
                    }
                }
                model.addRow(rowData);
            }

            model.addRow(new Object[]{"2024-03-01", 100.0, "Income", "Salary"});
            model.addRow(new Object[]{"2024-03-02", 50.0, "Income", "Bonus"});
            model.addRow(new Object[]{"2024-03-03", -20.0, "Expense", "Groceries"});

            table.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading table data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class CustomCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            double amount = Double.parseDouble(table.getValueAt(row, 1).toString());
            if (amount < 0) {
                component.setBackground(Color.RED);
            } else {
                component.setBackground(Color.GREEN);
            }

            return component;
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
