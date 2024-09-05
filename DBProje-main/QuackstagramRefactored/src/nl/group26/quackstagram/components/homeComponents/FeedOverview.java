package nl.group26.quackstagram.components.homeComponents;

import nl.group26.quackstagram.components.mainComponents.Post;
import nl.group26.quackstagram.database.PostsIOHandler;
import nl.group26.quackstagram.user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * FeedOverview is a component that contains all the posts in the feed of the user.
 */
public class FeedOverview extends JPanel {

    /**
     * Constructor for the FeedOverview component
     * @param user The user that the feed overview will be created for
     */
    public FeedOverview(User user) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        PostsIOHandler io = new PostsIOHandler();
        ArrayList<Post> feed = io.getFeed(user);

        for(Post post : feed) {
            io.receiveImageMetadata(post);
            add(new FeedPost(post));

            // Grey spacing panel
            JPanel spacingPanel = new JPanel();
            spacingPanel.setPreferredSize(new Dimension(WIDTH - 10, 5)); // Set the height for spacing
            spacingPanel.setBackground(new Color(230, 230, 230)); // Grey color for spacing
            add(spacingPanel);
        }
    }
}
