package com.jerem.mdd.service;

import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.AuthResponseDto;
import com.jerem.mdd.exception.UserNotFoundException;
import com.jerem.mdd.dto.AuthRequestDto;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Primary
public class DefaultAuthenticationService implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserManagementService userManagementService;


    public DefaultAuthenticationService(AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider, UserRepository userRepository,
            UserManagementService userManagementService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.userManagementService = userManagementService;
    }

    public AuthResponseDto authenticate(AuthRequestDto request) throws Exception {
        User user = userRepository.findByEmail(request.getIdentifier())
                .orElseGet(() -> userRepository.findByUsername(request.getIdentifier()).orElseThrow(
                        () -> new UsernameNotFoundException("Utilisateur non trouvé")));

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            user.getEmail(), request.getPassword()));

            String token = jwtTokenProvider.generateToken(authentication);

            return new AuthResponseDto(token);

        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException("Authentication failed", e);
        }


    }

    /**
     * Retrieves the email of the currently authenticated user.
     * <p>
     * This method accesses the {@link SecurityContextHolder} to obtain the authentication details.
     * </p>
     * 
     * @return an {@link Optional} containing the authenticated user's email, or empty if no user is
     *         authenticated
     */
    @Override
    public String getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            return authentication.getName();

        } else {

            throw new UsernameNotFoundException("User not authenticated");
        }
    }

    @Override
    public User getAuthenticatedUser() {
        return userManagementService.getUserEntityByEmail(getAuthenticatedUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not authenticated"));
    }



}
