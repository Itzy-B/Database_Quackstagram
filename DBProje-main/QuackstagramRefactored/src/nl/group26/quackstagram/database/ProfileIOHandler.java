package nl.group26.quackstagram.database;

import nl.group26.quackstagram.DataBaseConnection.DataBaseConnection;
import nl.group26.quackstagram.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles profile-related data operations.
 */
public class ProfileIOHandler {

    private User user;

    public ProfileIOHandler(User user) {
        this.user = user;
    }

    public String getBio() throws SQLException {
        Connection connection = DataBaseConnection.getConnection();
        String query = "SELECT bio FROM Users WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("bio");
        } else {
            return "";
        }
    }

    public int countImages() throws SQLException {
        Connection connection = DataBaseConnection.getConnection();
        String query = "SELECT COUNT(*) AS image_count FROM Posts WHERE user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("image_count");
        } else {
            return 0;
        }
    }

    public int[] getFollowersAndFollowing() throws SQLException {
        Connection connection = DataBaseConnection.getConnection();
        String queryFollowers = "SELECT COUNT(*) AS followers_count FROM follows WHERE following_id = ?";
        String queryFollowing = "SELECT COUNT(*) AS following_count FROM follows WHERE follower_id = ?";

        PreparedStatement preparedStatementFollowers = connection.prepareStatement(queryFollowers);
        preparedStatementFollowers.setInt(1, user.getId());
        ResultSet resultSetFollowers = preparedStatementFollowers.executeQuery();

        PreparedStatement preparedStatementFollowing = connection.prepareStatement(queryFollowing);
        preparedStatementFollowing.setInt(1, user.getId());
        ResultSet resultSetFollowing = preparedStatementFollowing.executeQuery();

        int followersCount = 0;
        int followingCount = 0;

        if (resultSetFollowers.next()) {
            followersCount = resultSetFollowers.getInt("followers_count");
        }

        if (resultSetFollowing.next()) {
            followingCount = resultSetFollowing.getInt("following_count");
        }

        return new int[]{followingCount, followersCount};
    }

    public boolean checkFollowed(User currentUser, User targetUser) throws SQLException {
        Connection connection = DataBaseConnection.getConnection();
        String query = "SELECT COUNT(*) AS followed_count FROM follows WHERE follower_id = ? AND following_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, currentUser.getId());
        preparedStatement.setInt(2, targetUser.getId());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("followed_count") > 0;
        } else {
            return false;
        }
    }

    public void followUser(User currentUser, User targetUser) throws SQLException {
        Connection connection = DataBaseConnection.getConnection();
        String query = "INSERT INTO follows (follower_id, following_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, currentUser.getId());
        preparedStatement.setInt(2, targetUser.getId());
        preparedStatement.executeUpdate();
    }
}
