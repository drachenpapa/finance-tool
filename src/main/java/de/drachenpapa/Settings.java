package de.drachenpapa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {
    private static final String SETTINGS_FILE = "financetool.properties";
    private final Properties properties;

    public Settings() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            // If the settings file doesn't exist, create a new one
        }
    }

    public void saveSettings(int x, int y, int width, int height) {
        properties.setProperty("x", String.valueOf(x));
        properties.setProperty("y", String.valueOf(y));
        properties.setProperty("width", String.valueOf(width));
        properties.setProperty("height", String.valueOf(height));

        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(output, "FinanceTool Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return Integer.parseInt(properties.getProperty("x", "100"));
    }

    public int getY() {
        return Integer.parseInt(properties.getProperty("y", "100"));
    }

    public int getWidth() {
        return Integer.parseInt(properties.getProperty("width", "400"));
    }

    public int getHeight() {
        return Integer.parseInt(properties.getProperty("height", "300"));
    }
}
