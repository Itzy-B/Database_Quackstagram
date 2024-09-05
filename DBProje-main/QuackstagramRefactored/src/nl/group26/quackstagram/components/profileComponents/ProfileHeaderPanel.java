package nl.group26.quackstagram.components.profileComponents;

import nl.group26.quackstagram.database.ProfileIOHandler;
import nl.group26.quackstagram.user.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * A custom JPanel that displays the profile image, stats and follow button of a user.
 * This panel is used in the ProfilePanel.
 */
public class ProfileHeaderPanel extends JPanel {

    private User user;
    private StatsPanel statsPanel;
    private ProfilePicture profilePicture;
    private JButton button;

    public ProfileHeaderPanel(User user) throws SQLException {
        ProfileIOHandler io = new ProfileIOHandler(user);
        String bio = io.getBio();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.GRAY);

        // Top Part of the Header (Profile Image, Stats, Follow Button)
        JPanel topHeaderPanel = new JPanel(new BorderLayout(10, 0));
        topHeaderPanel.setBackground(new Color(249, 249, 249));

        ProfilePicture profileImage = new ProfilePicture(user.getUsername());
        topHeaderPanel.add(profileImage.getProfileImage(), BorderLayout.WEST);

        topHeaderPanel.add(new StatsFollowPanel(user), BorderLayout.CENTER);

        add(topHeaderPanel);

        // Profile Name and Bio Panel
        JPanel profileNameAndBioPanel = new JPanel();
        profileNameAndBioPanel.setLayout(new BorderLayout());
        profileNameAndBioPanel.setBackground(new Color(249, 249, 249));

        JLabel profileNameLabel = new JLabel(user.getUsername());
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profileNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Padding on the sides

        JTextArea profileBio = new JTextArea(bio);
        profileBio.setEditable(false);
        profileBio.setFont(new Font("Arial", Font.PLAIN, 12));
        profileBio.setBackground(new Color(249, 249, 249));
        profileBio.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Padding on the sides

        profileNameAndBioPanel.add(profileNameLabel, BorderLayout.NORTH);
        profileNameAndBioPanel.add(profileBio, BorderLayout.CENTER);

        add(profileNameAndBioPanel);
    }
}
