package de.drachenpapa.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility class for retrieving localized messages.
 */
public class Messages {

    private static ResourceBundle messages;

    /**
     * Gets the default ResourceBundle for messages using the default locale.
     *
     * @return The default ResourceBundle.
     */
    public static ResourceBundle getMessages() {
        if (messages == null) {
            messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        }
        return messages;
    }

    /**
     * Gets the ResourceBundle for messages using the specified locale.
     *
     * @param locale The locale for which to retrieve the ResourceBundle.
     * @return The ResourceBundle for the specified locale.
     */
    public static ResourceBundle getMessages(Locale locale) {
        if (messages == null || !messages.getLocale().equals(locale)) {
            messages = ResourceBundle.getBundle("messages", locale);
        }
        return messages;
    }
}
