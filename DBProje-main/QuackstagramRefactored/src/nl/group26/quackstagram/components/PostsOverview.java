package nl.group26.quackstagram.components;

import nl.group26.quackstagram.components.mainComponents.Post;
import nl.group26.quackstagram.database.PostsIOHandler;
import nl.group26.quackstagram.pages.DetailedPostPage;
import nl.group26.quackstagram.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This class is a JPanel that displays a grid of images.
 * It can be used to display a grid of images from a specific user or from the explore page.
 */
public class PostsOverview extends JPanel {

    public DetailedPostPage openPost() {
        return null;
    }

    //for explore page
    public PostsOverview() {
        PostsIOHandler io = new PostsIOHandler();

        // Image Grid
        JPanel imageGridPanel = new JPanel(new GridLayout(0, 3, 2, 2)); // 3 columns, auto rows

        ArrayList<Post> posts = io.getPosts();
        for (Post post : posts) {
            JLabel imageLabel = post.getImageLabel();

            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    post.displayDetailed();
                }
            });
            imageGridPanel.add(imageLabel);
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(imageGridPanel);
    }

    //for a specific user
    public PostsOverview(User user) {
        PostsIOHandler io = new PostsIOHandler();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Image Grid
        JPanel imageGridPanel = new JPanel(new GridLayout(0, 3, 2, 2)); // 3 columns, auto rows

        ArrayList<Post> posts = io.getPostsFromUser(user);
        for (Post post : posts) {
            JLabel imageLabel = post.getImageLabel();

            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    post.displayDetailed();
                }
            });
            imageGridPanel.add(imageLabel);
        }

        JScrollPane scrollPane = new JScrollPane(imageGridPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);
    }
}
