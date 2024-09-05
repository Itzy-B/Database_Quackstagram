package nl.group26.quackstagram.components.mainComponents;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.pages.DetailedPostPage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static nl.group26.quackstagram.Quackstagram.IMAGE_SIZE;

/**
 * Represents a post in the Quackstagram application
 * Contains information about the post, such as the image, bio, timestamp, likes, etc.
 * Also contains methods to interact with the post, such as liking and unliking the post
 */
public class Post {

    private int id;  // New attribute for the post ID
    private String username;
    private String imageId;
    private String bio;
    private String timestampString;
    private int likes;
    private String imagePath;

    private JLabel imageLabel;

    // Constructor with id
    public Post(int id, String username, String imagePath, String bio, String timestampString, int likes) {
        this.id = id;
        this.username = username;
        this.imagePath = imagePath;
        this.bio = bio;
        this.timestampString = timestampString;
        this.likes = likes;
    }

    // Constructor without id (for new posts that haven't been saved to the database yet)
    public Post(String username, String imagePath, String bio, String timestampString, int likes) {
        this(-1, username, imagePath, bio, timestampString, likes);
    }

    public void displayDetailed() {
        Quackstagram.getInstance().redirectManager.redirect(new DetailedPostPage(this));
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(imageIcon);
        this.imageLabel = imageLabel;
    }

    public boolean likePost() {
        // Implement like post logic
        return false;
    }

    public boolean unlikePost() {
        // Implement unlike post logic
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTimestampString() {
        return timestampString;
    }

    public void setTimestampString(String timestampString) {
        this.timestampString = timestampString;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeSincePosting() {
        // Calculate time since posting
        String timeSincePosting = "Unknown";
        if (!timestampString.isEmpty()) {
            LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime now = LocalDateTime.now();
            long days = ChronoUnit.DAYS.between(timestamp, now);
            timeSincePosting = days + " day" + (days != 1 ? "s" : "") + " ago";
        }
        return timeSincePosting;
    }

    public JLabel getCroppedImageLabel(int imageWidth, int imageHeight) {
        JLabel imageLabel = new JLabel();
        try {
            BufferedImage originalImage = ImageIO.read(new File(getImagePath()));
            BufferedImage croppedImage = originalImage.getSubimage(0, 0, Math.min(originalImage.getWidth(), imageWidth), Math.min(originalImage.getHeight(), imageHeight));
            ImageIcon imageIcon = new ImageIcon(croppedImage);
            imageLabel.setIcon(imageIcon);
        } catch (IOException ex) {
            // Handle exception: Image file not found or reading error
            imageLabel.setText("Image not found");
        }
        return imageLabel;
    }
}
