package de.drachenpapa.views.renderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ResourceBundle;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private final ResourceBundle messages;

    public CustomTableCellRenderer(ResourceBundle messages) {
        this.messages = messages;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String amountColumnName = "Amount"; // TODO messages.getString("table.column.amount");
        int amountColumnIndex = -1;
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (table.getColumnName(i).equalsIgnoreCase(amountColumnName)) {
                amountColumnIndex = i;
                break;
            }
        }

        double amount = ((Number) table.getModel().getValueAt(row, amountColumnIndex)).doubleValue();
        if (amount < 0) {
            setBackground(new Color(255, 204, 203));
        } else {
            setBackground(new Color(144,238,144));
        }
        return this;
    }
}
