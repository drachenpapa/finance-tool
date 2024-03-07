package de.drachenpapa.views.dialogs;

import de.drachenpapa.utils.Messages;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AccountsDialog extends JDialog {

    public AccountsDialog(Frame owner, Locale locale) {
        super(owner, Messages.getMessages(locale).getString("accounts.title"), true);
        ResourceBundle messages = Messages.getMessages(locale);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(owner);

        JLabel aboutLabel = new JLabel("About your application");
        aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().add(aboutLabel, BorderLayout.CENTER);
    }
}
