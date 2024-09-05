package nl.group26.quackstagram.authPages;

import nl.group26.quackstagram.pages.Page;
import nl.group26.quackstagram.Quackstagram;

import javax.swing.*;
import java.awt.*;


/**
 * The AuthPage is an 'abstract' class that is used as a base for the RegisterPage and the LogInPage.
 */
public class AuthPage extends Page {
    private JPanel header;
    private JPanel mainPanel;

    public AuthPage(String title) {
        super(title);
        setLayout(new BorderLayout(10, 10));

        // Header with the Register label
        header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(new Color(51, 51, 51)); // Set a darker background for the header
        JLabel lblRegister = new JLabel("Quackstagram 🐥");
        lblRegister.setFont(new Font("Arial", Font.BOLD, 16));
        lblRegister.setForeground(Color.WHITE); // Set the text color to white
        header.add(lblRegister);
        header.setPreferredSize(new Dimension(Quackstagram.WINDOW_WIDTH, 40)); // Give the header a fixed height

        add(header, BorderLayout.NORTH);

    }

}
