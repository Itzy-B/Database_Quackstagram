package nl.group26.quackstagram.database;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.DataBaseConnection.DataBaseConnection;
import nl.group26.quackstagram.user.User;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is responsible for handling image upload operations.
 */
public class ImageUploadIOHandler extends IOHandler {

    public Path destPath;

    public boolean uploadProfilePicture(File file, String username) {
        try {
            BufferedImage image = ImageIO.read(file);
            File outputDirectory = new File(Quackstagram.PROFILE_PHOTO_STORAGE_PATH);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();  // Create the directory if it doesn't exist
            }
            String originalFileName = file.getName();
            File outputFile = new File(outputDirectory, originalFileName);
            ImageIO.write(image, "png", outputFile);

            saveProfilePictureName(username, originalFileName);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void saveProfilePictureName(String username, String profilePictureName) {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "UPDATE Users SET profile_picture = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, profilePictureName);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean uploadImage(File selectedFile, String bio) {
        try {
            User user = Quackstagram.getInstance().authenticationManager.loggedInUser;
            int userId = getUserId(user.getUsername());
            int imageId = getNextImageId(userId);
            String fileExtension = getFileExtension(selectedFile);
            String newFileName = user.getUsername() + "_" + imageId + "." + fileExtension;

            File outputDirectory = new File("img/uploaded");
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();  // Create the directory if it doesn't exist
            }
            File destPath = new File(outputDirectory, newFileName);
            Files.copy(selectedFile.toPath(), destPath.toPath(), StandardCopyOption.REPLACE_EXISTING);

            saveImageInfo(userId, newFileName, bio);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void saveImageInfo(int userId, String fileName, String bio) {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "INSERT INTO Posts (user_id, content, image_path, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, bio);
            preparedStatement.setString(3, fileName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getNextImageId(int userId) {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT COUNT(*) AS image_count FROM Posts WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("image_count") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;  // Default to 1 if there is an error
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private int getUserId(String username) {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT id FROM Users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Default to -1 if there is an error
    }
}
