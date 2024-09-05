package nl.group26.quackstagram.components.profileComponents;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.database.ProfileIOHandler;
import nl.group26.quackstagram.user.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Panel that displays the user's stats (number of posts, followers, following)
 * and a follow button
 */
public class StatsFollowPanel extends JPanel {

    public StatsFollowPanel(User user) throws SQLException {
        ProfileIOHandler io = new ProfileIOHandler(user);
        String bio = io.getBio();
        int postsCount = io.countImages();
        int followingCount = io.getFollowersAndFollowing()[0];
        int followersCount = io.getFollowersAndFollowing()[1];

        // Follow or Edit Profile Button
        JButton followButton;
        if (Quackstagram.getInstance().authenticationManager.loggedInUser.getUsername().equals(user.getUsername())) {
            followButton = new JButton("Edit Profile");
        } else {
            followButton = new JButton("Follow");

            if (io.checkFollowed(Quackstagram.getInstance().authenticationManager.loggedInUser, user)) {
                followButton.setText("Following");
            }

            followButton.addActionListener(e -> {
                try {
                    io.followUser(Quackstagram.getInstance().authenticationManager.loggedInUser, user);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                followButton.setText("Following");
            });
        }

        // Panel for stats
        JPanel statsPanel = new JPanel(new GridLayout(1, 3));
        statsPanel.add(createStatLabel("Posts", postsCount));
        statsPanel.add(createStatLabel("Followers", followersCount));
        statsPanel.add(createStatLabel("Following", followingCount));

        // Add Stats and Follow Button to a combined Panel
        setLayout(new BorderLayout());
        add(statsPanel, BorderLayout.NORTH);
        add(followButton, BorderLayout.SOUTH);
    }

    private JLabel createStatLabel(String label, int count) {
        JLabel statLabel = new JLabel("<html><center>" + count + "<br>" + label + "</center></html>", JLabel.CENTER);
        statLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statLabel.setVerticalAlignment(SwingConstants.CENTER);
        return statLabel;
    }
}
