package nl.group26.quackstagram.pages;

import nl.group26.quackstagram.database.IOHandler;
import nl.group26.quackstagram.user.User;

import java.awt.*;

/** This class is responsible for the notification page.
 * It shows the notifications of the user.
 */
public class NotificationPage extends QuackstagramPage {

    public NotificationPage() {
        super ("Notifications");
    }

    private User user;
    private IOHandler ioHandler;

    private ScrollPane scrollPane;


}
