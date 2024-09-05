package nl.group26.quackstagram.authPages;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.pages.ProfilePage;
import nl.group26.quackstagram.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 * The LogInPage is a class that is used to create and visualize the LogIn page.
 *
 */
public class LogInPage extends AuthPage {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton signInButton;
    private JButton registerButton;

    public LogInPage() {
        super("Quackstagram - LogIn");

        // Profile picture placeholder without border
        JLabel logo = new JLabel();
        logo.setPreferredSize(new Dimension(80, 80));
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.setVerticalAlignment(JLabel.CENTER);
        logo.setIcon(new ImageIcon(new ImageIcon("img/logos/DACS.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        JPanel photoPanel = new JPanel(); // Use a panel to center the photo label
        photoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        photoPanel.add(logo);

        // Text fields panel
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        usernameField = new JTextField("Username");
        passwordField = new JPasswordField("Password");
        usernameField.setForeground(Color.GRAY);
        passwordField.setForeground(Color.GRAY);

        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(photoPanel);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(usernameField);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(passwordField);
        fieldsPanel.add(Box.createVerticalStrut(10));


        // Register button with black text
        signInButton = new JButton("Sign-In");
        signInButton.addActionListener(e -> {
            try {
                onSignInClicked(e);
            } catch (SQLException f) {
                // TODO Auto-generated catch block
                f.printStackTrace();
            }
        });
        signInButton.setBackground(new Color(255, 90, 95)); // Use a red color that matches the mockup
        signInButton.setForeground(Color.BLACK); // Set the text color to black
        signInButton.setFocusPainted(false);
        signInButton.setBorderPainted(false);
        signInButton.setFont(new Font("Arial", Font.BOLD, 14));
        JPanel registerPanel = new JPanel(new BorderLayout()); // Panel to contain the register button
        registerPanel.setBackground(Color.WHITE); // Background for the panel
        registerPanel.add(signInButton, BorderLayout.CENTER);

        // New button for navigating to SignUpUI
        registerButton = new JButton("No Account? Register Now");
        registerButton.addActionListener(this::onRegisterNowClicked);
        registerButton.setBackground(Color.WHITE); // Set a different color for distinction
        registerButton.setForeground(Color.BLACK);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);

        // Panel to hold both buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // Grid layout with 1 row, 2 columns
        buttonPanel.setBackground(Color.white);
        buttonPanel.add(signInButton);
        buttonPanel.add(registerButton);


        add(fieldsPanel, BorderLayout.CENTER);
        add(registerPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Redirects to the ProfilePage when username and password are correct. Otherwise, shows an error message.
     * @param event The event that triggered the action.
     * @throws SQLException 
     */
    private void onSignInClicked(ActionEvent event) throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println(username + password);
        if(Quackstagram.getInstance().authenticationManager.logIn(username, password)) {
            Quackstagram.getInstance().redirectManager.redirect( new ProfilePage(username));
        } else {
            JOptionPane.showMessageDialog(this, "Wrong username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Redirects to the RegisterPage when the button is clicked.
     * @param event The event that triggered the action.
     */
    private void onRegisterNowClicked(ActionEvent event) {
        Quackstagram.getInstance().redirectManager.redirect( new RegisterPage());
    }

}
