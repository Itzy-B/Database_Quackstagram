package nl.group26.quackstagram.authPages;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.database.AuthHandler;
import nl.group26.quackstagram.database.ImageUploadIOHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The RegisterPage is a class that is used to create and visualize the Register page.
 */
public class RegisterPage extends AuthPage {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField bioField;
    private JComboBox<String> genderDropdown;
    private File selectedProfilePicture;

    private JButton uploadButton;
    private JButton registerButton;
    private JButton signInButton;

    public RegisterPage() {
        super("Quackstagram - Register");

        // Profile picture placeholder without border
        JLabel lblPhoto = new JLabel();
        lblPhoto.setPreferredSize(new Dimension(80, 80));
        lblPhoto.setHorizontalAlignment(JLabel.CENTER);
        lblPhoto.setVerticalAlignment(JLabel.CENTER);
        lblPhoto.setIcon(new ImageIcon(new ImageIcon("img/logos/DACS.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        JPanel photoPanel = new JPanel(); // Use a panel to center the photo label
        photoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        photoPanel.add(lblPhoto);

        // Text fields panel
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        usernameField = new JTextField("Username");
        passwordField = new JPasswordField("Password");
        bioField = new JTextField("Bio");

        usernameField.setForeground(Color.GRAY);
        passwordField.setForeground(Color.GRAY);
        bioField.setForeground(Color.GRAY);

        genderDropdown = new JComboBox<>(new String[]{"Male", "Female"});
        genderDropdown.setForeground(Color.GRAY);

        fieldsPanel.add(usernameField);
        fieldsPanel.add(passwordField);
        fieldsPanel.add(bioField);
        fieldsPanel.add(genderDropdown);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        uploadButton = new JButton("Upload Profile Picture");
        registerButton = new JButton("Register");
        signInButton = new JButton("Sign In");

        buttonsPanel.add(uploadButton);
        buttonsPanel.add(registerButton);
        buttonsPanel.add(signInButton);

        // Main layout
        setLayout(new BorderLayout());
        add(photoPanel, BorderLayout.NORTH);
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                onRegisterClicked(event);
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                onSignInClicked(event);
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                uploadProfilePicture(event);
            }
        });
    }

    private boolean onRegisterClicked(ActionEvent event) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String bio = bioField.getText();
        String gender = (String) genderDropdown.getSelectedItem();

        AuthHandler authHandler = new AuthHandler();
        String result = authHandler.saveCredentials(username, password, bio, gender);

        switch (result) {
            case "SUCCESS":
                if (selectedProfilePicture != null) {
                    ImageUploadIOHandler imageUploadHandler = new ImageUploadIOHandler();
                    imageUploadHandler.uploadProfilePicture(selectedProfilePicture, username);
                }
                Quackstagram.getInstance().redirectManager.redirect(new LogInPage());
                break;
            case "DUPLICATE":
                JOptionPane.showMessageDialog(this, "There is already a user called \"" + username + "\" in the database.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        return true;
    }

    private void onSignInClicked(ActionEvent event) {
        Quackstagram.getInstance().redirectManager.redirect(new LogInPage());
    }

    private void uploadProfilePicture(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedProfilePicture = fileChooser.getSelectedFile();
        }
    }
}
