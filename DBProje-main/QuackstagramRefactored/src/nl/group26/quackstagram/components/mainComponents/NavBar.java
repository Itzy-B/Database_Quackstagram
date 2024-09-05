package nl.group26.quackstagram.components.mainComponents;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.pages.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * NavBar is a component that represents a navigation bar at the bottom of the page.
 * It is used to navigate between different pages of the application.
 */
public class NavBar extends JPanel {

    public NavBar() {
        setBackground(new Color(249, 249, 249));
        setLayout(new GridLayout(1, 5)); // Adjust layout to ensure buttons are properly aligned

        add(createIconButton("img/icons/home.png", "home"));
        add(createIconButton("img/icons/search.png", "explore"));
        add(createIconButton("img/icons/add.png", "add"));
        add(createIconButton("img/icons/heart.png", "notification"));
        add(createIconButton("img/icons/profile.png", "profile"));
    }

    private JButton createIconButton(String iconPath, String buttonType) {
        ImageIcon iconOriginal = new ImageIcon(iconPath);
        Image iconScaled = iconOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);

        String loggedInUsername = Quackstagram.getInstance().authenticationManager.loggedInUser.getUsername();

        button.addActionListener(e -> {
            switch (buttonType) {
                case "home":
                    Quackstagram.getInstance().redirectManager.redirect(new HomePage());
                    break;
                case "profile":
                    try {
                        Quackstagram.getInstance().redirectManager.redirect(new ProfilePage(loggedInUsername));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "notification":
                    Quackstagram.getInstance().redirectManager.redirect(new NotificationPage());
                    break;
                case "explore":
                    Quackstagram.getInstance().redirectManager.redirect(new ExplorePage());
                    break;
                case "add":
                    Quackstagram.getInstance().redirectManager.redirect(new ImageUploadPage());
                    break;
            }
        });

        return button;
    }
}
