package nl.group26.quackstagram.pages;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.components.homeComponents.FeedOverview;
import nl.group26.quackstagram.components.mainComponents.NavBar;
import nl.group26.quackstagram.user.User;

import javax.swing.*;
import java.awt.*;

public class HomePage extends QuackstagramPage {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel homePanel;
    private JPanel imageViewPanel;
    private User user;

    public HomePage() {
        super("Home");
        this.user = Quackstagram.getInstance().authenticationManager.loggedInUser;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        homePanel = new JPanel(new BorderLayout());
        imageViewPanel = new JPanel(new BorderLayout());

        FeedOverview feedOverview = new FeedOverview(user);
        JScrollPane scrollPane = new JScrollPane(feedOverview);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        homePanel.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(homePanel, "Home");
        cardPanel.add(imageViewPanel, "ImageView");

        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "Home"); // Start with the home view

        // Adding the NavBar to the bottom
        NavBar navigationPanel = new NavBar();
        add(navigationPanel, BorderLayout.SOUTH);
    }
}
