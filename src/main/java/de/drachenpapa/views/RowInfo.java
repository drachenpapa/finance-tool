package de.drachenpapa.views;

import javax.swing.*;
import java.awt.*;

class RowInfo {

    static void show(JTable table, Object[] rowData) {
        JFrame infoFrame = createInfoFrame();
        JPanel panel = createPanel(table, rowData);
        infoFrame.add(panel);
        infoFrame.setVisible(true);
    }

    private static JFrame createInfoFrame() {
        JFrame infoFrame = new JFrame("Row Information");
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoFrame.setSize(300, 150);
        return infoFrame;
    }

    private static JPanel createPanel(JTable table, Object[] rowData) {
        JPanel panel = new JPanel(new FlowLayout());
        for (int i = 0; i < rowData.length; i++) {
            JLabel label = new JLabel(table.getColumnName(i) + ": " + rowData[i]);
            panel.add(label);
        }
        return panel;
    }
}
