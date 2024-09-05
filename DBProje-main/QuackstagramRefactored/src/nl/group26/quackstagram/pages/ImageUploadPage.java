package nl.group26.quackstagram.pages;

import nl.group26.quackstagram.database.ImageUploadIOHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;

import static nl.group26.quackstagram.Quackstagram.WINDOW_HEIGHT;
import static nl.group26.quackstagram.Quackstagram.WINDOW_WIDTH;

/** This class is responsible for the image upload page.
 * It allows the user to upload an image and add a caption.
 */
public class ImageUploadPage extends QuackstagramPage {

    private JLabel imagePreviewLabel;
    private JTextArea bioTextArea;
    private JButton uploadButton;
    private JButton saveButton;

    private ImageUploadIOHandler io;

    public ImageUploadPage() {
        super("Upload");

        io = new ImageUploadIOHandler();

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Image preview
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePreviewLabel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT / 3));

        // Set an initial empty icon to the imagePreviewLabel
        ImageIcon emptyImageIcon = new ImageIcon();
        imagePreviewLabel.setIcon(emptyImageIcon);

        contentPanel.add(imagePreviewLabel);

        // Bio text area
        bioTextArea = new JTextArea("Enter a caption");
        bioTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        JScrollPane bioScrollPane = new JScrollPane(bioTextArea);
        bioScrollPane.setPreferredSize(new Dimension(WINDOW_WIDTH - 50, WINDOW_HEIGHT / 6));
        contentPanel.add(bioScrollPane);

        // Upload button
        uploadButton = new JButton("Upload Image");
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.addActionListener(this::uploadAction);
        contentPanel.add(uploadButton);

        // Save button (for bio)
        saveButton = new JButton("Save Caption");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(this::saveBioAction);


        add(contentPanel, BorderLayout.CENTER);
    }

    public void uploadAction(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);

        if(returnValue == JFileChooser.APPROVE_OPTION) {
            boolean uploaded = io.uploadImage(fileChooser.getSelectedFile(), bioTextArea.getText());
            if(uploaded) {
                updatePreview();
                JOptionPane.showMessageDialog(this, "Image uploaded and preview updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Error saving image." , "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    private void updatePreview() {
        // Load the image from the saved path
        ImageIcon imageIcon = new ImageIcon(io.destPath.toString());

        // Check if imagePreviewLabel has a valid size
        if (imagePreviewLabel.getWidth() > 0 && imagePreviewLabel.getHeight() > 0) {
            Image image = imageIcon.getImage();

            // Calculate the dimensions for the image preview
            int previewWidth = imagePreviewLabel.getWidth();
            int previewHeight = imagePreviewLabel.getHeight();
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            double widthRatio = (double) previewWidth / imageWidth;
            double heightRatio = (double) previewHeight / imageHeight;
            double scale = Math.min(widthRatio, heightRatio);
            int scaledWidth = (int) (scale * imageWidth);
            int scaledHeight = (int) (scale * imageHeight);

            // Set the image icon with the scaled image
            imageIcon.setImage(image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        }
        imagePreviewLabel.setIcon(imageIcon);

        // Change the text of the upload button
        uploadButton.setText("Upload Another Image");
    }


    private void saveBioAction(ActionEvent event) {
        // Here you would handle saving the bio text
        String bioText = bioTextArea.getText();
        // For example, save the bio text to a file or database
        JOptionPane.showMessageDialog(this, "Caption saved: " + bioText);
    }




}
