package nl.group26.quackstagram.components.profileComponents;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.components.mainComponents.Post;
import nl.group26.quackstagram.database.PostsIOHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * DeleteButton
 * A button that deletes a post
 */
public class DeleteButton extends JButton implements ActionListener {

    private Post post;

    public DeleteButton(Post post) {
        super("Delete");
        this.post = post;
        addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        PostsIOHandler io = new PostsIOHandler();
        try {
            io.deletePost(post);
            Quackstagram.getInstance().redirectManager.redirectBack();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
