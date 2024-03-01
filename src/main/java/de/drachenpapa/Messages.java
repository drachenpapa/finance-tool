package de.drachenpapa;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    private static ResourceBundle messages;

    public static ResourceBundle getMessages() {
        if (messages == null) {
            messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        }
        return messages;
    }

    public static ResourceBundle getMessages(Locale locale) {
        if (messages == null || !messages.getLocale().equals(locale)) {
            messages = ResourceBundle.getBundle("messages", locale);
        }
        return messages;
    }
}
