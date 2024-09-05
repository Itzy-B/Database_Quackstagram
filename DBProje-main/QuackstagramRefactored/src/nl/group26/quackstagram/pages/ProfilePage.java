package nl.group26.quackstagram.pages;

import nl.group26.quackstagram.components.mainComponents.NavBar;
import nl.group26.quackstagram.components.profileComponents.ProfileHeaderPanel;
import nl.group26.quackstagram.components.PostsOverview;
import nl.group26.quackstagram.user.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * This class is responsible for displaying the profile page.
 * It shows the user's profile image, stats, bio and their posts.
 */
public class ProfilePage extends QuackstagramPage {

    public ProfilePage(String username) throws SQLException {
        super("Profile");

        User user = new User(username);

        setLayout(new BorderLayout());

        ProfileHeaderPanel headerPanel = new ProfileHeaderPanel(user);
        add(headerPanel, BorderLayout.NORTH);

        PostsOverview postsOverview = new PostsOverview(user);
        JScrollPane scrollPane = new JScrollPane(postsOverview);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }
}
