package de.drachenpapa.views;

import de.drachenpapa.Messages;
import de.drachenpapa.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsDialog extends JDialog {
    private final JComboBox<String> languageComboBox;

    public SettingsDialog(Frame owner, Locale locale) {
        super(owner, Messages.getMessages(locale).getString("settings.title"), true);
        ResourceBundle messages = Messages.getMessages(locale);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridLayout(2, 1));

        languageComboBox = new JComboBox<>(new String[]{
                messages.getString("settings.lang.en"),
                messages.getString("settings.lang.de")});
        setInitialLanguage(languageComboBox, locale);
        panel.add(languageComboBox);

        JButton saveButton = new JButton(messages.getString("settings.save"));
        saveButton.addActionListener(e -> {
            saveSettings();
            dispose();
        });
        panel.add(saveButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void setInitialLanguage(JComboBox<String> languageComboBox, Locale locale) {
        languageComboBox.setSelectedIndex(locale.getLanguage().equals("de") ? 1 : 0);
    }

    private void saveSettings() {
        Locale locale = languageComboBox.getSelectedIndex() == 0 ? Locale.ENGLISH : Locale.GERMAN;
        new Settings().saveLocale(locale);
        dispose();
        TableView.restart((JFrame) SwingUtilities.getWindowAncestor(this));
    }
}
