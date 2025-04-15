package com.jerem.mdd.service;

import org.springframework.security.authentication.AuthenticationManager;
import com.jerem.mdd.dto.AuthResponseDto;
import com.jerem.mdd.model.User;
import com.jerem.mdd.dto.AuthRequestDto;

public interface AuthenticationService {

    /**
     * Authenticates a user based on the provided login credentials.
     * <p>
     * This method uses {@link AuthenticationManager} to verify the user's identity and get a JWT
     * token upon successful authentication with the help of {@link jwtFactory}.
     * </p>
     * 
     * @param {@link AuthRequestDto} the login request containing the user's identifier and password
     * @return a {@link AuthResponseDto} containing the generated authentication response
     * @throws Exception if authentication fails
     */
    public AuthResponseDto authenticate(AuthRequestDto request) throws Exception;

    public User getAuthenticatedUser();


}
