package de.drachenpapa.views;

import de.drachenpapa.database.DatabaseConnector;
import de.drachenpapa.database.records.Account;
import de.drachenpapa.database.records.Category;
import de.drachenpapa.database.records.FinancesEntry;
import de.drachenpapa.utils.Messages;
import de.drachenpapa.utils.Settings;
import de.drachenpapa.views.components.MenuBar;
import de.drachenpapa.views.components.Toolbar;
import de.drachenpapa.views.dialogs.DetailsDialog;
import de.drachenpapa.views.renderer.CustomTableCellRenderer;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
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
        List<FinancesEntry> finances = DatabaseConnector.getFinances();
        List<Account> accounts = DatabaseConnector.getAccounts();
        List<Category> categories = DatabaseConnector.getCategories();

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }
        };

        // TODO
        List<String> columns = List.of("ID", "Date", "Amount", "Description", "Account", "Category");
        for (String column : columns) {
            model.addColumn(column);
        }

        for (FinancesEntry financesEntry : finances) {
            Object[] rowData = new Object[] {financesEntry.id(), financesEntry.date(), financesEntry.amount(), financesEntry.description(), accounts.get(financesEntry.accountId()-1).name(), categories.get(financesEntry.categoryId()-1).name()};
            model.addRow(rowData);
        }

        table.setModel(model);
    }

    public static void restart(JFrame currentFrame) {
        EventQueue.invokeLater(() -> {
            currentFrame.dispose();
            TableView newApp = new TableView();
            newApp.setVisible(true);
        });
    }
}
