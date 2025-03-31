package com.jerem.mdd.service;

import org.springframework.security.authentication.AuthenticationManager;
import com.jerem.mdd.dto.AuthResponse;
import com.jerem.mdd.dto.LoginRequest;

public interface AuthenticationService {

    /**
     * Authenticates a user based on the provided login credentials.
     * <p>
     * This method uses {@link AuthenticationManager} to verify the user's identity and get a JWT
     * token upon successful authentication with the help of {@link jwtFactory}.
     * </p>
     * 
     * @param {@link LoginRequest} the login request containing the user's identifier and password
     * @return a {@link AuthResponse} containing the generated authentication response
     * @throws Exception if authentication fails
     */
    public AuthResponse authenticate(LoginRequest request) throws Exception;

    public String getAuthenticatedUserEmail();



}
