package com.jerem.mdd.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.UserDetailedDto;
import com.jerem.mdd.dto.UserUpdateRequestDto;
import com.jerem.mdd.exception.EmailAlreadyExistException;
import com.jerem.mdd.exception.UserNotFoundException;
import com.jerem.mdd.exception.UsernameAlreadyExistException;
import com.jerem.mdd.mapper.UserMapper;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;



/**
 * Service class for user profile operations, including updating and retrieving profile information.
 * <p>
 * This service focuses on actions related to the authenticated user's profile and should not
 * utilize any implementation of {@link UserManagementService} to maintain a strict separation of
 * responsibilities.
 * </p>
 */
@Service
@Slf4j
public class UserProfileService {


    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;


    public UserProfileService(UserRepository userRepository,
            AuthenticationService authenticationService, UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;

    }

    /**
     * Retrieves the profile details of the currently authenticated user.
     *
     * @return a {@link UserDetailedDto} representing the authenticated user's profile
     * @throws UserNotFoundException if the user is not found in the repository
     */

    public UserDetailedDto getUserProfile() {

        return userMapper.toUserProfileDto(getAuthenticatedUser());
    }


    /**
     * Updates the profile of the currently authenticated user with the provided data.
     * <p>
     * This method performs uniqueness checks on email and username, and encodes the password if it
     * has changed.
     * </p>
     *
     * @param userUpdateRequestDto the DTO containing updated profile data
     * @return a {@link UserDetailedDto} representing the updated user profile
     * @throws EmailAlreadyExistException if the new email is already taken by another user
     * @throws UsernameAlreadyExistException if the new username is already taken by another user
     * @throws UserNotFoundException if the user cannot be found in the repository
     */
    public UserDetailedDto updateUserProfile(UserUpdateRequestDto userUpdateRequestDto) {
        User authenticatedUser = getAuthenticatedUser();

        if (!userUpdateRequestDto.getEmail().equals(authenticatedUser.getEmail())
                && userRepository.existsByEmail(userUpdateRequestDto.getEmail())) {
            throw new EmailAlreadyExistException("Email already exists",
                    "UserProfileService.updateUserProfile");
        }

        if (!userUpdateRequestDto.getUsername().equals(authenticatedUser.getUsername())
                && userRepository.existsByUsername(userUpdateRequestDto.getUsername())) {
            throw new UsernameAlreadyExistException("Username already exists",
                    "UserProfileService.updateUserProfile");
        }



        authenticatedUser.setEmail(userUpdateRequestDto.getEmail());
        authenticatedUser.setUsername(userUpdateRequestDto.getUsername());


        if (userUpdateRequestDto.getPassword() != null
                && !passwordEncoder.matches(userUpdateRequestDto.getPassword(),
                        authenticatedUser.getPassword())) {
            authenticatedUser
                    .setPassword(passwordEncoder.encode(userUpdateRequestDto.getPassword()));
        }

        userRepository.save(authenticatedUser);

        return userMapper.toUserProfileDto(authenticatedUser);

    }

    /**
     * Retrieves the currently authenticated user from the repository using their email.
     *
     * @return the authenticated {@link User} entity
     * @throws UserNotFoundException if the user cannot be found in the repository
     */
    private User getAuthenticatedUser() {
        String authenticatedUserEmail = authenticationService.getAuthenticatedUser().getEmail();

        return userRepository.findByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }



}
