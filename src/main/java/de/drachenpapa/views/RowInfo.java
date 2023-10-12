package de.drachenpapa.views;

import javax.swing.*;
import java.awt.*;

class RowInfo {

    static void show(JTable table, Object[] rowData) {
        JFrame infoFrame = new JFrame("Row Information");
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infoFrame.setSize(300, 150);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        for (int i = 0; i < rowData.length; i++) {
            JLabel label = new JLabel(table.getColumnName(i) + ": " + rowData[i]);
            panel.add(label);
        }

        infoFrame.add(panel);
        infoFrame.setVisible(true);
    }
}
