package nl.group26.quackstagram.pages;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.components.mainComponents.NavBar;
import nl.group26.quackstagram.components.mainComponents.Post;
import nl.group26.quackstagram.components.profileComponents.DeleteButton;
import nl.group26.quackstagram.database.PostsIOHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static nl.group26.quackstagram.Quackstagram.WINDOW_WIDTH;

/**
 * This class is responsible for displaying a detailed view of a post.
 * It shows the image, username, bio and likes of the post.
 */

public class DetailedPostPage extends Page {

    private JButton back;
    private Post post;

    public DetailedPostPage(Post post) {
        super("Post");
        PostsIOHandler io = new PostsIOHandler();
        io.receiveImageMetadata(post);

        setLayout(new BorderLayout());

        JPanel imageViewerPanel = new JPanel(new BorderLayout());

        String imagePath = post.getImagePath();

        // Top panel for username and time since posting
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton usernameLabel = new JButton(post.getUsername());
        JLabel timeLabel = new JLabel(post.getTimeSincePosting());
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
        topPanel.add(usernameLabel, BorderLayout.WEST);
        topPanel.add(timeLabel, BorderLayout.EAST);

        // Prepare the image for display
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            ImageIcon imageIcon = new ImageIcon(originalImage);
            imageLabel.setIcon(imageIcon);
        } catch (IOException ex) {
            imageLabel.setText("Image not found");
        }

        // Bottom panel for bio and likes
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JTextArea bioTextArea = new JTextArea(post.getBio());
        bioTextArea.setEditable(false);
        JLabel likesLabel = new JLabel("Likes: " + post.getLikes());
        likesLabel.setBackground(Color.WHITE);

        bottomPanel.add(bioTextArea, BorderLayout.CENTER);
        bottomPanel.add(likesLabel, BorderLayout.SOUTH);

        if(post.getUsername().equals(Quackstagram.getInstance().authenticationManager.loggedInUser.getUsername())) {
            DeleteButton delete = new DeleteButton(post);
            bottomPanel.add(delete, BorderLayout.EAST);
        }

        // Adding the components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Panel for the back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");

        // Make the button take up the full width
        backButton.setPreferredSize(new Dimension(WINDOW_WIDTH-20, backButton.getPreferredSize().height));

        backButtonPanel.add(backButton);

        backButton.addActionListener(e -> {
            Quackstagram.getInstance().redirectManager.redirectBack();
        });

        usernameLabel.addActionListener(e -> {
            try {
                Quackstagram.getInstance().redirectManager.redirect(new ProfilePage(post.getUsername()));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        // Container panel for image and details
        JPanel containerPanel = new JPanel(new BorderLayout());

        containerPanel.add(topPanel, BorderLayout.NORTH);
        containerPanel.add(imageLabel, BorderLayout.CENTER);
        containerPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add the container panel and back button panel to the frame
        add(backButtonPanel, BorderLayout.NORTH);
        add(containerPanel, BorderLayout.CENTER);

        NavBar navigationPanel = new NavBar();
        add(navigationPanel, BorderLayout.SOUTH);
    }
}
