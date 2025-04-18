package com.jerem.mdd.service;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jerem.mdd.model.AppUserDetails;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link UserManagementService} responsible for handling user-related operations.
 * <p>
 * This service provides functionalities for user creation, retrieval, and validation. It interacts
 * with the {@link UserRepository} to persist and fetch user data. It uses the
 * {@link PasswordEncoder} to hash the password before storing in the database.
 * </p>
 */
@Service
@Slf4j
public class DefaultUserManagementService implements UserManagementService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a {@code DefaultUserManagementService} with the necessary dependencies.
     *
     * @param userRepository the repository handling user persistence
     * @param passwordEncoder the encoder for hashing user passwords
     */
    public DefaultUserManagementService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUserDetails createUser(String email, String plainPassword, String username) {


        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(plainPassword));
        user.setUsername(username);

        userRepository.save(user);

        return new AppUserDetails(user);
    }

    @Override
    public boolean isEmailAlreadyUsed(String email) {
        log.debug(email + " looking for in database");
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isUsernameAlreadyUsed(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Optional<User> getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email);
    }



}
