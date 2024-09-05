package nl.group26.quackstagram.components.profileComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that displays the stats of a user such as the number of posts, followers and following
 * it is used in the StatsFollowPanel

 */
public class StatsPanel extends JPanel {

    public StatsPanel(int postsCount, int followersCount, int followingCount) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        setBackground(new Color(249, 249, 249));
        System.out.println("Number of posts for this user " + postsCount);
        add(createStatLabel(Integer.toString(postsCount), "Posts"));
        add(createStatLabel(Integer.toString(followersCount), "Followers"));
        add(createStatLabel(Integer.toString(followingCount), "Following"));
        setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0)); // Add some vertical padding
    }

    private JLabel createStatLabel(String number, String text) {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + number + "<br/>" + text + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        return label;
    }

}
