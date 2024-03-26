package de.drachenpapa.views;

import de.drachenpapa.database.AccountsDB;
import de.drachenpapa.database.CategoriesDB;
import de.drachenpapa.database.H2Connector;
import de.drachenpapa.database.TransactionsDB;
import de.drachenpapa.database.records.Account;
import de.drachenpapa.database.records.Category;
import de.drachenpapa.database.records.Transaction;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
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

        setupFrame();

        table = new JTable();
        configureTable();

        addComponentsToFrame();

        loadTableData();
    }

    private void setupFrame() {
        setTitle(messages.getString("app.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(settings.getWidth(), settings.getHeight());
        setLocation(settings.getX(), settings.getY());
        setJMenuBar(new MenuBar(this).create());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                settings.saveWindowSettings(getX(), getY(), getWidth(), getHeight());
            }
        });
    }

    private void configureTable() {
        table.setDefaultEditor(Object.class, null);
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(messages));

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

    private void addComponentsToFrame() {
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(Toolbar.create(table, messages, this), BorderLayout.PAGE_START);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public void loadTableData() {
        String[] columns = {
                messages.getString("table.column.id"),
                messages.getString("table.column.date"),
                messages.getString("table.column.amount"),
                messages.getString("table.column.description"),
                messages.getString("table.column.account_id"),
                messages.getString("table.column.category_id")
        };

        List<Object[]> rowData = getRowData();
        DefaultTableModel model = new DefaultTableModel(rowData.toArray(new Object[0][]), columns);
        table.setModel(model);
    }

    private List<Object[]> getRowData() {
        List<Object[]> rowData = new ArrayList<>();
        List<Account> accounts = AccountsDB.get();
        List<Category> categories = CategoriesDB.get();
        for (Transaction transaction : TransactionsDB.get()) {
            Object[] rowDataEntry = {
                    transaction.id(),
                    transaction.date(),
                    transaction.amount(),
                    transaction.description(),
                    accounts.get(transaction.accountId() - 1).name(),
                    categories.get(transaction.categoryId() - 1).name()
            };
            rowData.add(rowDataEntry);
        }
        return rowData;
    }

    public static void restart(JFrame currentFrame) {
        EventQueue.invokeLater(() -> {
            currentFrame.dispose();
            TableView newApp = new TableView();
            newApp.setVisible(true);
        });
    }
}
