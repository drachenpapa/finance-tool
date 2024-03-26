package de.drachenpapa.views.dialogs;

import de.drachenpapa.utils.Messages;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AboutDialog extends JDialog {

    public AboutDialog(Frame owner, Locale locale) {
        super(owner, Messages.getMessages(locale).getString("about.title"), true);
        ResourceBundle messages = Messages.getMessages(locale);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(owner);

        // Icon
//        ImageIcon icon = new ImageIcon(getClass().getResource("/path/to/your/icon.png"));
//        JLabel iconLabel = new JLabel(icon);

        // About information
        JLabel aboutLabel = new JLabel("About your application");
        aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        JLabel aboutLabel = new JLabel("<html><div style='text-align: center;'>"
//                + "<h2>About This App</h2>"
//                + "<p><b>Version:</b> [Insert version number]</p>"
//                + "<p><b>Developer:</b> [Insert developer/company name]</p>"
//                + "<p><b>Copyright:</b> © [Insert year] [Developer/Company Name]</p>"
//                + "<p><b>Description:</b> [Brief description of the application's purpose or main features]</p>"
//                + "</div></html>");

        JPanel contentPanel = new JPanel(new BorderLayout());
//        contentPanel.add(iconLabel, BorderLayout.WEST);
        contentPanel.add(aboutLabel, BorderLayout.CENTER);

        getContentPane().add(contentPanel);
    }
}
