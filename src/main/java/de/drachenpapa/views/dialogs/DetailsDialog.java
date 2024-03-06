package de.drachenpapa.views.dialogs;

import de.drachenpapa.database.DatabaseConnector;
import de.drachenpapa.views.TableView;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DetailsDialog {

    private static final int FIELD_WIDTH = 20;

    private static JXDatePicker datePicker;
    private static JTextField amountField;
    private static JTextField categoryField;
    private static JTextField descriptionField;
    private static JFrame infoFrame;
    private static String id;

    public static void show(TableView tableView, JTable table, Object[] rowData, ResourceBundle messages) {
        id = rowData[0].toString();
        infoFrame = createInfoFrame(table, messages);
        JPanel panel = createPanel(tableView, rowData, messages);
        infoFrame.add(panel);
        infoFrame.setVisible(true);
    }

    private static JFrame createInfoFrame(JTable table, ResourceBundle messages) {
        JFrame infoFrame = new JFrame(messages.getString("details.title"));
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoFrame.setSize(400, 300);
        infoFrame.setUndecorated(true);
        infoFrame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        infoFrame.setLocationRelativeTo(table);
        return infoFrame;
    }

    private static JPanel createPanel(TableView tableView, Object[] rowData, ResourceBundle messages) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addDateComponents(panel, gbc, rowData, messages);
        addAmountComponents(panel, gbc, rowData, messages);
        addCategoryComponents(panel, gbc, rowData, messages);
        addDescriptionComponents(panel, gbc, rowData, messages);

        JButton saveButton = new JButton(messages.getString("details.save"));
        saveButton.addActionListener(e -> saveData(tableView));

        JButton cancelButton = new JButton(messages.getString("details.cancel"));
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

        if (id.isEmpty()) {
            DatabaseConnector.insertFinancesEntry(date, amount, category, description);
        } else {
            DatabaseConnector.updateFinancesEntry(id, date, amount, category, description);
        }

        tableView.loadTableData();
        infoFrame.dispose();
    }

    private static void addDateComponents(JPanel panel, GridBagConstraints gbc, Object[] rowData, ResourceBundle messages) {
        JLabel dateLabel = new JLabel(messages.getString("details.date") + ":");
        datePicker = new JXDatePicker();
        Date date = rowData.length > 1 ? parseDate(rowData[1].toString()) : new Date();
        datePicker.setDate(date);

        addComponent(panel, dateLabel, datePicker, gbc);
    }

    private static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    private static void addAmountComponents(JPanel panel, GridBagConstraints gbc, Object[] rowData, ResourceBundle messages) {
        JLabel amountLabel = new JLabel(messages.getString("details.amount") + ":");
        amountField = new JTextField(getFieldText(rowData, 2), FIELD_WIDTH);
        amountField.setEditable(true);

        addComponent(panel, amountLabel, amountField, gbc);
    }

    private static void addCategoryComponents(JPanel panel, GridBagConstraints gbc, Object[] rowData, ResourceBundle messages) {
        JLabel categoryLabel = new JLabel(messages.getString("details.category") + ":");
        categoryField = new JTextField(getFieldText(rowData, 3), FIELD_WIDTH);
        categoryField.setEditable(true);

        addComponent(panel, categoryLabel, categoryField, gbc);
    }

    private static void addDescriptionComponents(JPanel panel, GridBagConstraints gbc, Object[] rowData, ResourceBundle messages) {
        JLabel descriptionLabel = new JLabel(messages.getString("details.description") + ":");
        descriptionField = new JTextField(getFieldText(rowData, 4), FIELD_WIDTH);
        descriptionField.setEditable(true);

        addComponent(panel, descriptionLabel, descriptionField, gbc);
    }

    private static String getFieldText(Object[] rowData, int index) {
        return rowData.length > index ? rowData[index].toString() : "";
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
