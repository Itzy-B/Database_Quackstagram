package nl.group26.quackstagram.authentication;

import nl.group26.quackstagram.Quackstagram;
import nl.group26.quackstagram.database.AuthHandler;
import nl.group26.quackstagram.user.User;

import javax.swing.*;

/**
 * This class is responsible for managing the authentication of the user
 * It is part of the Quackstagram.java's singularity principle
 */
public class AuthenticationManager {


    //Global variable
    public User loggedInUser;

    /**
     * Checks credentials and creates a new user which will be the logged in user
     * @param username from input field
     * @param password from input field
     * @return true if username/password are correct
     */
    public boolean logIn(String username, String password) {
        AuthHandler io = new AuthHandler();

        if(io.verifyCredentials(username, password)) {
            loggedInUser = new User(username);
            return true;
        }

        return false;
    }

    public boolean logOut() {
        return false;
    }

    /**
     * Checks if username isn't taken and then creates a new user
     * @param username from input field
     * @param password from input field
     * @param bio from input field
     * @param gender from dropdown menu
     * @return true if signup was successful
     */
    public boolean signUp(String username, String password, String bio, String gender) {
        AuthHandler io = new AuthHandler();
        if (io.checkExistingUsername(username)) {
            JOptionPane.showMessageDialog(Quackstagram.getInstance(), "Username already exists, Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        io.saveCredentials(username, password, bio, gender);
        return true;
    }

    /**
     * Checks if the user is logged in
     * @return true if user is logged in
     */
    public boolean isLoggedIn() {
        return this.loggedInUser != null;
    }



}
