package nl.group26.quackstagram.components.exploreComponents;

import javax.swing.*;
import java.awt.*;

import static nl.group26.quackstagram.Quackstagram.WINDOW_WIDTH;

/**
 * SearchPanel is a component that contains a search bar at the top of the ExplorePanel.
 */
public class SearchPanel extends JPanel {
    private JTextField searchField;

    public SearchPanel() {
        // Search bar at the top
        setLayout(new BorderLayout());
        JTextField searchField = new JTextField(" Search Users                                                   ");
        searchField.setSize(WINDOW_WIDTH, searchField.getPreferredSize().height);
        add(searchField, BorderLayout.CENTER);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, searchField.getPreferredSize().height)); // Limit the height
    }

}
