package de.drachenpapa.views;

import de.drachenpapa.Messages;
import de.drachenpapa.Settings;

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
        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu(messages.getString("file.menu"));
        fileMenu.add(getSettingsMenu());
        fileMenu.add(getExitMenu());

        return fileMenu;
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
        setExitMenuItemAccelerator(exitMenuItem);
        exitMenuItem.addActionListener(e -> {
            saveSettingsAndExit();
        });
        return exitMenuItem;
    }

    private void setExitMenuItemAccelerator(JMenuItem exitMenuItem) {
        exitMenuItem.setMnemonic(KeyEvent.VK_Q);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
    }

    private void saveSettingsAndExit() {
        Rectangle bounds = parentFrame.getBounds();
        settings.saveSettings(bounds.x, bounds.y, bounds.width, bounds.height);
        System.exit(0);
    }
}
