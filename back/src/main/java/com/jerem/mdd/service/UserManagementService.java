package com.jerem.mdd.service;

import org.springframework.security.core.userdetails.UserDetails;


/**
 * Service interface for managing users. It defines methods for create or retrieve informations.
 * <p>
 * This service defines the contract for managing users.
 * 
 * </p>
 * 
 * @see DefaultMessageService for the default implementation.
 */
public interface UserManagementService {

    /**
     * Creates a new user with the given details and returns a UserDetails object. This method is
     * responsible for creating a new user in the application.
     * 
     * @param email the email address of the new user.
     * @param plainPassword the plain-text password of the new user
     * @param username the name of the new user.
     * @return a UserDetails object that represents the created user.
     */
    public UserDetails createUser(String email, String plainPassword, String username);


    /**
     * Retrieves a {@link UserDetails} object by email. This object should be cast to its
     * implementation.
     * 
     * @param {@link String} the email.
     * @return a {@link UserDetails}
     */
    public UserDetails getUserbyEmail(String email);

    /**
     * Retrieves a {@link UserDetails} object by username. This object should be cast to its
     * implementation.
     * 
     * @param {@link String} the username.
     * @return a {@link UserDetails}
     */
    public UserDetails getUserbyUsername(String username);

    /**
     * Return true if the email is already present in the Database.
     * 
     * @param {@link String} email of the user
     * @return a {@link boolean} true if the user email already exist, and false otherwise
     */
    public boolean isEmailAlreadyUsed(String email);

    /**
     * Return true if the username is already present in the Database.
     * 
     * @param {@link String} username of the user
     * @return a {@link boolean} true if the username already exist, and false otherwise
     */
    public boolean isUsernameAlreadyUsed(String username);

}
