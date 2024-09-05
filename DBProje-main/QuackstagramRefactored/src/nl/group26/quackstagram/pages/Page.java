package nl.group26.quackstagram.pages;

import javax.swing.*;

/**
 * This class is responsible for displaying a page.
 * It is the parent class of all pages.
 */
public class Page extends JPanel {

    private String title;

    public Page(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
