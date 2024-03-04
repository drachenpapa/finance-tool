package de.drachenpapa.views;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

class RowInfo {

    static void show(JTable table, Object[] rowData, ResourceBundle messages) {
        JFrame infoFrame = createInfoFrame(table, messages);
        JPanel panel = createPanel(table, rowData, messages, infoFrame);
        infoFrame.add(panel);
        infoFrame.setVisible(true);
    }

    private static JFrame createInfoFrame(JTable table, ResourceBundle messages) {
        JFrame infoFrame = new JFrame(messages.getString("rowinfo.title"));
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoFrame.setLayout(new BorderLayout());
        infoFrame.setLocationRelativeTo(table);
        infoFrame.setSize(400, 300);
        infoFrame.setUndecorated(true);
        infoFrame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        return infoFrame;
    }

    private static JPanel createPanel(JTable table, Object[] rowData, ResourceBundle messages, JFrame infoFrame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addDateComponents(panel, gbc, messages, rowData);
        addAmountComponents(panel, gbc, messages, rowData);
        addCategoryComponents(panel, gbc, messages, rowData);
        addDescriptionComponents(panel, gbc, messages, rowData);

        JButton saveButton = createButton(messages.getString("rowinfo.save"), e -> infoFrame.dispose());
        JButton cancelButton = createButton(messages.getString("rowinfo.cancel"), e -> infoFrame.dispose());

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

    private static void addDateComponents(JPanel panel, GridBagConstraints gbc, ResourceBundle messages, Object[] rowData) {
        JLabel dateLabel = new JLabel(messages.getString("rowinfo.date") + ":");
        JXDatePicker datePicker = new JXDatePicker();
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(rowData[0].toString());
        } catch (ParseException e) {
            date = new Date();
        }
        datePicker.setDate(date);

        addComponent(panel, dateLabel, datePicker, gbc);
    }

    private static void addAmountComponents(JPanel panel, GridBagConstraints gbc, ResourceBundle messages, Object[] rowData) {
        JLabel amountLabel = new JLabel(messages.getString("rowinfo.amount") + ":");
        JTextField amountField = new JTextField(rowData[1].toString(), 20);
        amountField.setEditable(true);

        addComponent(panel, amountLabel, amountField, gbc);
    }

    private static void addCategoryComponents(JPanel panel, GridBagConstraints gbc, ResourceBundle messages, Object[] rowData) {
        JLabel categoryLabel = new JLabel(messages.getString("rowinfo.category") + ":");
        JTextField categoryField = new JTextField(rowData[2].toString(), 20);
        categoryField.setEditable(true);

        addComponent(panel, categoryLabel, categoryField, gbc);
    }

    private static void addDescriptionComponents(JPanel panel, GridBagConstraints gbc, ResourceBundle messages, Object[] rowData) {
        JLabel descriptionLabel = new JLabel(messages.getString("rowinfo.description") + ":");
        JTextField descriptionField = new JTextField(rowData[3].toString(), 20);
        descriptionField.setEditable(true);

        addComponent(panel, descriptionLabel, descriptionField, gbc);
    }

    private static JButton createButton(String label, ActionListener actionListener) {
        JButton button = new JButton(label);
        button.addActionListener(actionListener);
        return button;
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
