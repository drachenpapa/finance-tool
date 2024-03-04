package de.drachenpapa.views.dialogs;

import de.drachenpapa.Messages;
import de.drachenpapa.Settings;
import de.drachenpapa.views.TableView;

import javax.swing.*;
import java.awt.*;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsDialog extends JDialog {
    private final JComboBox<String> languageComboBox;
    private final JComboBox<Currency> currencyComboBox;

    public SettingsDialog(Frame owner, Locale locale) {
        super(owner, Messages.getMessages(locale).getString("settings.title"), true);
        ResourceBundle messages = Messages.getMessages(locale);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        languageComboBox = new JComboBox<>(new String[]{
                messages.getString("settings.lang.en"),
                messages.getString("settings.lang.de")});
        setInitialLanguage(languageComboBox, locale);
        panel.add(languageComboBox);

        currencyComboBox = new JComboBox<>(Currency.getAvailableCurrencies().toArray(new Currency[0]));
        setInitialCurrency(currencyComboBox);
        panel.add(currencyComboBox);

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

    private void setInitialCurrency(JComboBox<Currency> currencyComboBox) {
        String savedCurrency = new Settings().getSavedCurrency();
        Currency defaultCurrency = Currency.getInstance("EUR");
        currencyComboBox.setSelectedItem(savedCurrency != null ? Currency.getInstance(savedCurrency) : defaultCurrency);
    }

    private void saveSettings() {
        Locale locale = languageComboBox.getSelectedIndex() == 0 ? Locale.ENGLISH : Locale.GERMAN;
        Currency selectedCurrency = (Currency) currencyComboBox.getSelectedItem();
        new Settings().saveSettings(locale, selectedCurrency.getCurrencyCode());
        dispose();
        TableView.restart((JFrame) SwingUtilities.getWindowAncestor(this));
    }
}
