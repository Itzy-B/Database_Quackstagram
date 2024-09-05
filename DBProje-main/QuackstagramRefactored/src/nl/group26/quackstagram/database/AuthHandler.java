package nl.group26.quackstagram.database;

import nl.group26.quackstagram.DataBaseConnection.DataBaseConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 * This class is responsible for handling authentication related operations.
 * It can save new user credentials, verify existing credentials and check if a username already exists.
 * It uses SHA-256 hashing with a random salt for password hashing.
 */
public class AuthHandler {

    // Helper methods for password hashing and salt generation
    private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        return md.digest(password.getBytes());
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public String saveCredentials(String username, String password, String bio, String gender) {
        byte[] salt = generateSalt();
        try {
            byte[] hashedPassword = hashPassword(password, salt);
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashedPasswordBase64 = Base64.getEncoder().encodeToString(hashedPassword);

            Connection connection = DataBaseConnection.getConnection();
            String query = "INSERT INTO Users (username, password, bio, gender, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPasswordBase64 + ":" + saltBase64);
            preparedStatement.setString(3, bio);
            preparedStatement.setString(4, gender);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return "SUCCESS";
            } else {
                return "FAILURE";
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { 
                return "DUPLICATE";
            }
            e.printStackTrace();
            return "FAILURE";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "FAILURE";
        }
    }

    public boolean verifyCredentials(String username, String password) {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT password FROM Users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String[] storedPasswordAndSalt = resultSet.getString("password").split(":");
                byte[] storedPassword = Base64.getDecoder().decode(storedPasswordAndSalt[0]);
                byte[] storedSalt = Base64.getDecoder().decode(storedPasswordAndSalt[1]);

                byte[] hashedInputPassword = hashPassword(password, storedSalt);

                return MessageDigest.isEqual(storedPassword, hashedInputPassword);
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistingUsername(String username) {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT username FROM Users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
