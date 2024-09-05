package nl.group26.quackstagram.components.mainComponents;

import javax.swing.*;
import java.awt.*;

import static nl.group26.quackstagram.Quackstagram.WINDOW_WIDTH;

/**
 * Header is a component that represents a header at every page.
 * It is used to display the title of the page to the user
 */
public class Header extends JPanel {

    private String headerText;

    public Header(String headerText) {
        this.headerText = headerText;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(new Color(51, 51, 51));
        JLabel text = new JLabel(headerText);
        text.setFont(new Font("Arial", Font.BOLD, 16));
        text.setForeground(Color.WHITE);
        add(text);
        setPreferredSize(new Dimension(WINDOW_WIDTH, 40));
    }
}
