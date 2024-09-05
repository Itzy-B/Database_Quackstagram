package nl.group26.quackstagram.pages;

import nl.group26.quackstagram.components.PostsOverview;
import nl.group26.quackstagram.components.exploreComponents.SearchPanel;
import nl.group26.quackstagram.components.mainComponents.NavBar;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for displaying the explore page.
 * It shows a search bar and a list of posts.
 */
public class ExplorePage extends QuackstagramPage {

    public ExplorePage() {
        super("Explore");

        setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        content.add(new SearchPanel(), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(new PostsOverview());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        content.add(scrollPane, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        // Adding the NavBar to the bottom
        NavBar navigationPanel = new NavBar();
        add(navigationPanel, BorderLayout.SOUTH);
    }
}
