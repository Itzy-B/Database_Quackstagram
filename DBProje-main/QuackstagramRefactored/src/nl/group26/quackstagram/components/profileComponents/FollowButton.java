package nl.group26.quackstagram.components.profileComponents;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JButton that is used to follow a user.
 */
public class FollowButton extends JButton {
    
    public FollowButton(String text) {
        super(text);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setFont(new Font("Arial", Font.BOLD, 12));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getMinimumSize().height)); // Make the button fill the horizontal space
        setBackground(new Color(225, 228, 232)); // A soft, appealing color that complements the UI
        setForeground(Color.BLACK);
        setOpaque(true);
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some vertical padding
    }
}
