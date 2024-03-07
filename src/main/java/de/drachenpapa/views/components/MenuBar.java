package de.drachenpapa.views.components;

import de.drachenpapa.utils.Messages;
import de.drachenpapa.utils.Settings;
import de.drachenpapa.views.TableView;
import de.drachenpapa.views.dialogs.AboutDialog;
import de.drachenpapa.views.dialogs.AccountsDialog;
import de.drachenpapa.views.dialogs.CategoriesDialog;
import de.drachenpapa.views.dialogs.SettingsDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class MenuBar {

    private final Settings settings;
    private final TableView parentFrame;
    private final ResourceBundle messages;

    public MenuBar(TableView parentFrame) {
        this.parentFrame = parentFrame;
        this.settings = parentFrame.getSettings();
        this.messages = Messages.getMessages(this.settings.getLocale());
    }

    public JMenuBar create() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createHelpMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu(messages.getString("file.menu"));
        fileMenu.add(createMenuItem(messages.getString("file.settings"), this::showSettingsDialog));
        fileMenu.add(createMenuItem(messages.getString("file.exit"), this::exitApplication));
        return fileMenu;
    }

    private JMenu createEditMenu() {
        JMenu helpMenu = new JMenu(messages.getString("edit.menu"));
        helpMenu.add(createMenuItem(messages.getString("edit.accounts"), this::showAccountsDialog));
        helpMenu.add(createMenuItem(messages.getString("edit.categories"), this::showCategoriesDialog));
        return helpMenu;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu(messages.getString("help.menu"));
        helpMenu.add(createMenuItem(messages.getString("help.about"), this::showAboutDialog));
        return helpMenu;
    }

    private JMenuItem createMenuItem(String label, Runnable action) {
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.addActionListener(e -> action.run());
        return menuItem;
    }

    private void showSettingsDialog() {
        SettingsDialog settingsDialog = new SettingsDialog(parentFrame, settings.getLocale());
        settingsDialog.setVisible(true);
    }

    private void exitApplication() {
        Rectangle bounds = parentFrame.getBounds();
        settings.saveWindowSettings(bounds.x, bounds.y, bounds.width, bounds.height);
        System.exit(0);
    }

    private void showAccountsDialog() {
        AccountsDialog accountsDialog = new AccountsDialog(parentFrame, settings.getLocale());
        accountsDialog.setVisible(true);
    }

    private void showCategoriesDialog() {
        CategoriesDialog categoriesDialog = new CategoriesDialog(parentFrame, settings.getLocale());
        categoriesDialog.setVisible(true);
    }

    private void showAboutDialog() {
        AboutDialog aboutDialog = new AboutDialog(parentFrame, settings.getLocale());
        aboutDialog.setVisible(true);
    }
}
