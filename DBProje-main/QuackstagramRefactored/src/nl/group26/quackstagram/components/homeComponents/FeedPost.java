package nl.group26.quackstagram.components.homeComponents;

import nl.group26.quackstagram.components.mainComponents.Post;
import nl.group26.quackstagram.database.PostsIOHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static nl.group26.quackstagram.Quackstagram.WINDOW_WIDTH;

/**
 * FeedPost is a component that represents a post in the feed of the user.
 */
public class FeedPost extends JPanel {

    private static final int IMAGE_WIDTH = WINDOW_WIDTH - 100; // Width for the image posts
    private static final int IMAGE_HEIGHT = 150; // Height for the image posts

    private static final Color LIKE_BUTTON_COLOR = new Color(255, 90, 95); // Color for the like button


    /**
     * Constructor for the FeedPost component
     * @param post The post that the FeedPost will represent
     */
    public FeedPost(Post post) {
        PostsIOHandler io = new PostsIOHandler();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE); // Set the background color for the item panel
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setAlignmentX(CENTER_ALIGNMENT);
        JLabel nameLabel = new JLabel(post.getUsername());
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel image = post.getCroppedImageLabel(IMAGE_WIDTH, IMAGE_HEIGHT);

        JLabel descriptionLabel = new JLabel(post.getBio());
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel likesLabel = new JLabel("Likes: " + post.getLikes());
        likesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton likeButton = new JButton("❤");
        likeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        likeButton.setBackground(LIKE_BUTTON_COLOR); // Set the background color for the like button
        likeButton.setOpaque(true);
        likeButton.setBorderPainted(false); // Remove border
        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                io.receiveImageMetadata(post);

                io.likePost(post);
                // Update the UI
                io.receiveImageMetadata(post);
                likesLabel.setText("Likes: " + post.getLikes());
            }
        });


        add(nameLabel);
        add(image);
        add(descriptionLabel);
        add(likesLabel);
        add(likeButton);


        // Make the image clickable
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO displayImage(postData); // Call a method to switch to the image view
                post.displayDetailed();
            }
        });
    }
}
