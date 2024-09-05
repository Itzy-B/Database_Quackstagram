package nl.group26.quackstagram.database;

import nl.group26.quackstagram.DataBaseConnection.DataBaseConnection;
import nl.group26.quackstagram.components.mainComponents.Post;
import nl.group26.quackstagram.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * PostsIOHandler handles posts data operations.
 */
public class PostsIOHandler {

    // Method to get the feed of posts for a specific user
    public ArrayList<Post> getFeed(User user) {
        ArrayList<Post> feed = new ArrayList<>();
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT posts.*, users.username FROM posts JOIN users ON posts.user_id = users.id " +
                           "WHERE users.id IN (SELECT following_id FROM follows WHERE follower_id = (SELECT id FROM users WHERE username = ?)) " +
                           "OR users.username = ? ORDER BY posts.created_at DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String imagePath = resultSet.getString("image_path");
                String bio = resultSet.getString("content");
                String timestamp = resultSet.getString("created_at");
                int likes = resultSet.getInt("likes");

                Post post = new Post(id, username, imagePath, bio, timestamp, likes);
                post.setImageLabel(imagePath);
                feed.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feed;
    }
    

    public ResultSet getAllPosts() {
        ResultSet resultSet = null;
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT posts.*, users.username FROM posts JOIN users ON posts.user_id = users.id ORDER BY posts.created_at DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getPostsFromUser(String username) {
        ResultSet resultSet = null;
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT * FROM posts WHERE user_id = (SELECT id FROM users WHERE username = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getPostById(int postId) {
        ResultSet resultSet = null;
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT * FROM posts WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void deletePost(Post post) throws SQLException {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "DELETE FROM posts WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, post.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting post");
        }
    }

    // Method to save a new post to the database
    public void savePost(Post post, int userId) throws SQLException {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "INSERT INTO posts (user_id, content, image_path, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, post.getBio());
            preparedStatement.setString(3, post.getImagePath());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error saving post");
        }
    }

    // Method to get all posts
    public ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT posts.*, users.username FROM posts JOIN users ON posts.user_id = users.id ORDER BY posts.created_at DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String imagePath = resultSet.getString("image_path");
                String bio = resultSet.getString("content");
                String timestamp = resultSet.getString("created_at");
                int likes = resultSet.getInt("likes"); 

                Post post = new Post(id, username, imagePath, bio, timestamp, likes);
                post.setImageLabel(imagePath);
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // Method to get posts from a specific user
    public ArrayList<Post> getPostsFromUser(User user) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT * FROM posts WHERE user_id = (SELECT id FROM users WHERE username = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String imagePath = resultSet.getString("image_path");
                String bio = resultSet.getString("content");
                String timestamp = resultSet.getString("created_at");
                int likes = resultSet.getInt("likes"); 

                Post post = new Post(id, user.getUsername(), imagePath, bio, timestamp, likes);
                post.setImageLabel(imagePath);
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // Method to receive image metadata and update the post object
    public void receiveImageMetadata(Post post) {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "SELECT posts.*, users.username FROM posts JOIN users ON posts.user_id = users.id WHERE posts.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, post.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                post.setUsername(resultSet.getString("username"));
                post.setBio(resultSet.getString("content"));
                post.setImagePath(resultSet.getString("image_path"));
                post.setTimestampString(resultSet.getString("created_at"));
                // Add likes column to posts table if necessary
                post.setLikes(resultSet.getInt("likes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to like a post and update the database
    public void likePost(Post post) {
        try {
            Connection connection = DataBaseConnection.getConnection();
            String query = "UPDATE posts SET likes = likes + 1 WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, post.getId());
            preparedStatement.executeUpdate();
            receiveImageMetadata(post); // Update post data
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
