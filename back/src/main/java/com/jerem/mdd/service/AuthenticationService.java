package com.jerem.mdd.service;

import com.jerem.mdd.dto.AuthResponseDto;
import com.jerem.mdd.model.User;
import com.jerem.mdd.dto.AuthRequestDto;


/**
 * Service interface responsible for handling user authentication and access to the authenticated
 * user.
 */
public interface AuthenticationService {

    /**
     * Authenticates a user based on the provided login credentials.
     * <p>
     * This method uses {@link org.springframework.security.authentication.AuthenticationManager} to
     * verify the user's identity and generate a JWT token using {@code jwtFactory}.
     * </p>
     *
     * @param request the login request containing the user's identifier and password
     * @return an {@link AuthResponseDto} containing the generated authentication response
     * @throws Exception if authentication fails
     */
    public AuthResponseDto authenticate(AuthRequestDto request) throws Exception;

    /**
     * Returns the currently authenticated user.
     *
     * @return the authenticated {@link User}
     */
    public User getAuthenticatedUser();


}
