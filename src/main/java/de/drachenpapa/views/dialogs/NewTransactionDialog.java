package de.drachenpapa.views.dialogs;

import de.drachenpapa.database.H2Connector;
import de.drachenpapa.database.TransactionsDB;
import de.drachenpapa.views.TableView;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class NewTransactionDialog {

    private static final int FIELD_WIDTH = 20;

    private static JXDatePicker datePicker;
    private static JTextField amountField;
    private static JTextField categoryField;
    private static JTextField descriptionField;
    private static JFrame infoFrame;

    public static void show(TableView tableView, JTable table, ResourceBundle messages) {
        infoFrame = createInfoFrame(table, messages);
        JPanel panel = createPanel(tableView, messages);
        infoFrame.add(panel);
        infoFrame.setVisible(true);
    }

    private static JFrame createInfoFrame(JTable table, ResourceBundle messages) {
        JFrame infoFrame = new JFrame(messages.getString("transaction.title"));
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoFrame.setSize(400, 300);
        infoFrame.setUndecorated(true);
        infoFrame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        infoFrame.setLocationRelativeTo(table);
        return infoFrame;
    }

    private static JPanel createPanel(TableView tableView, ResourceBundle messages) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addDateComponents(panel, gbc, messages);
        addAmountComponents(panel, gbc, messages);
        addCategoryComponents(panel, gbc, messages);
        addDescriptionComponents(panel, gbc, messages);

        JButton saveButton = new JButton(messages.getString("transaction.save"));
        saveButton.addActionListener(e -> saveData(tableView));

        JButton cancelButton = new JButton(messages.getString("transaction.cancel"));
        cancelButton.addActionListener(e -> infoFrame.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private static void saveData(TableView tableView) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(datePicker.getDate());
        String amount = amountField.getText();
        String category = categoryField.getText();
        String description = descriptionField.getText();

        TransactionsDB.insert(date, amount, category, description);

        tableView.loadTableData();
        infoFrame.dispose();
    }

    private static void addDateComponents(JPanel panel, GridBagConstraints gbc, ResourceBundle messages) {
        JLabel dateLabel = new JLabel(messages.getString("transaction.date") + ":");
        datePicker = new JXDatePicker(new Date());

        addComponent(panel, dateLabel, datePicker, gbc);
    }

    private static void addAmountComponents(JPanel panel, GridBagConstraints gbc, ResourceBundle messages) {
        JLabel amountLabel = new JLabel(messages.getString("transaction.amount") + ":");
        amountField = new JTextField(FIELD_WIDTH);
        amountField.setEditable(true);

        addComponent(panel, amountLabel, amountField, gbc);
    }

    private static void addCategoryComponents(JPanel panel, GridBagConstraints gbc, ResourceBundle messages) {
        JLabel categoryLabel = new JLabel(messages.getString("transaction.category") + ":");
        categoryField = new JTextField(FIELD_WIDTH);
        categoryField.setEditable(true);

        addComponent(panel, categoryLabel, categoryField, gbc);
    }

    private static void addDescriptionComponents(JPanel panel, GridBagConstraints gbc, ResourceBundle messages) {
        JLabel descriptionLabel = new JLabel(messages.getString("transaction.description") + ":");
        descriptionField = new JTextField(FIELD_WIDTH);
        descriptionField.setEditable(true);

        addComponent(panel, descriptionLabel, descriptionField, gbc);
    }

    private static void addComponent(JPanel panel, JLabel label, Component field, GridBagConstraints gbc) {
        panel.add(label, gbc);
        gbc.gridx++;
        panel.add(field, gbc);
        resetGridBagConstraints(gbc);
    }

    private static void resetGridBagConstraints(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
    }
}
