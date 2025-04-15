package com.jerem.mdd.service;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
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

    public DefaultAuthenticationService(AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;

    }

    /**
     * authenticate the user given their credentials in request
     * <p>
     * The user is retrieved using identifier which can be email or username
     * </p>
     * 
     * @return an {@link AuthResponseDto} the response containing de token
     */
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
     * Retrieves the authenticated user.
     * <p>
     * This method accesses the {@link SecurityContextHolder} to obtain the authentication. Then it
     * retrieves the claim id from Jwt
     * </p>
     * 
     * @return an {@link User} the authenticated user
     */
    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof Jwt) {
                Jwt jwt = (Jwt) principal;
                Long userId = jwt.getClaim("id");
                log.debug("" + jwt.getClaim("id"));
                log.debug(jwt.getClaim("username"));
                log.debug(jwt.getClaim("email"));

                return userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found"));
            }
        }
        throw new UserNotFoundException("User not found");
    }

}
