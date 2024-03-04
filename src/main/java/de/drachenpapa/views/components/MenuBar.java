package de.drachenpapa.views.components;

import de.drachenpapa.Messages;
import de.drachenpapa.Settings;
import de.drachenpapa.views.dialogs.SettingsDialog;
import de.drachenpapa.views.TableView;
import de.drachenpapa.views.dialogs.AboutDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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
        menuBar.add(createHelpMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu(messages.getString("file.menu"));
        fileMenu.add(getSettingsMenu());
        fileMenu.add(getExitMenu());

        return fileMenu;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu(messages.getString("help.menu"));
        helpMenu.add(getAboutMenu());

        return helpMenu;
    }

    private JMenuItem getSettingsMenu() {
        JMenuItem settingsMenuItem = new JMenuItem(messages.getString("file.settings"));
        settingsMenuItem.addActionListener(e -> {
            SettingsDialog settingsDialog = new SettingsDialog(parentFrame, settings.getLocale());
            settingsDialog.setVisible(true);
        });
        return settingsMenuItem;
    }

    private JMenuItem getExitMenu() {
        JMenuItem exitMenuItem = new JMenuItem(messages.getString("file.exit"));
        exitMenuItem.setMnemonic(KeyEvent.VK_Q);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        exitMenuItem.addActionListener(e -> {
            Rectangle bounds = parentFrame.getBounds();
            settings.saveSettings(bounds.x, bounds.y, bounds.width, bounds.height);
            System.exit(0);
        });
        return exitMenuItem;
    }

    private JMenuItem getAboutMenu() {
        JMenuItem aboutMenuItem = new JMenuItem(messages.getString("help.about"));
        aboutMenuItem.addActionListener(e -> {
            AboutDialog aboutDialog = new AboutDialog(parentFrame, settings.getLocale());
            aboutDialog.setVisible(true);
        });
        return aboutMenuItem;
    }
}
