package nl.group26.quackstagram.redirect;

import nl.group26.quackstagram.pages.Page;
import nl.group26.quackstagram.pages.QuackstagramPage;
import nl.group26.quackstagram.authPages.AuthPage;

import javax.swing.*;

/**
 * This class is responsible for redirecting the user to a different page.
 * It is used to change the content/title of the frame.
 */
public class RedirectManager {

    private JFrame frame;

    private Page currentPanel;
    private Page lastPanel;
    private String lastTitle;

    public RedirectManager(JFrame frame) {
        this.frame = frame;
    }

    public void redirect(QuackstagramPage to) {
        SwingUtilities.invokeLater(() -> {
            if(currentPanel != null) {
                frame.remove(currentPanel);
                lastPanel = currentPanel;
                lastTitle = frame.getTitle();
            }
            currentPanel = to;
            frame.getContentPane().add(to);
            frame.setTitle(to.getTitle());
            frame.revalidate();
        });
    }

    public void redirect(Page to) {
        SwingUtilities.invokeLater(() -> {
            if(currentPanel != null) {
                frame.remove(currentPanel);
                lastPanel = currentPanel;
                lastTitle = frame.getTitle();
            }

            currentPanel = to;
            frame.getContentPane().add(to);
            frame.setTitle(to.getTitle());
            frame.revalidate();
        });
    }

    public void redirect(AuthPage to) {
        SwingUtilities.invokeLater(() -> {
            if(currentPanel != null) {
                frame.remove(currentPanel);
                lastPanel = currentPanel;
                lastTitle = frame.getTitle();
            }
            currentPanel = to;
            frame.getContentPane().add(to);
            frame.setTitle(to.getTitle());
            frame.revalidate();
        });
    }

    public void redirectBack() {
        SwingUtilities.invokeLater(() -> {
            frame.remove(currentPanel);
            frame.getContentPane().add(lastPanel);
            frame.setTitle(lastTitle);
            frame.repaint();
            frame.revalidate();

            currentPanel = lastPanel;
        });
    }
}
