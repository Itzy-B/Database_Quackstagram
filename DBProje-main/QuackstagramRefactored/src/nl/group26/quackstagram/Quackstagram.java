package nl.group26.quackstagram;

import nl.group26.quackstagram.authPages.LogInPage;
import nl.group26.quackstagram.authentication.AuthenticationManager;
import nl.group26.quackstagram.redirect.RedirectManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class is responsible for the main method of the application.
 * The instance of the application is created here and is singular.
 */

public class Quackstagram extends JFrame {

    public static Quackstagram instance;

    public final RedirectManager redirectManager;
    public final AuthenticationManager authenticationManager;

    public static final int WINDOW_WIDTH = 300;
    public static final int WINDOW_HEIGHT = 500;
    public static final int NAV_ICON_SIZE = 20; // Size for navigation icons
    public static final int IMAGE_SIZE = WINDOW_WIDTH / 3; // Size for each image in the grid

    public static final String PROFILE_PHOTO_STORAGE_PATH = "img/storage/profile";
    public static String CREDENTIALS_FILE_PATH = "data/credentials.txt";


    public Quackstagram() {
        if (instance != null) {
            throw new IllegalStateException("Quackstagram is already initialized");
        }
        this.redirectManager = new RedirectManager(this);
        this.authenticationManager = new AuthenticationManager();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        redirectManager.redirect(new LogInPage());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            instance = new Quackstagram();
            instance.setVisible(true);
        });
    }

    public static Quackstagram getInstance() {
        return instance;
    }

}
