package com.jerem.mdd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jerem.mdd.dto.AuthResponseDto;
import com.jerem.mdd.dto.RegisterRequestDto;
import com.jerem.mdd.dto.UserSummaryDto;
import com.jerem.mdd.dto.AuthRequestDto;
import com.jerem.mdd.service.AuthenticationService;
import com.jerem.mdd.service.DefaultAuthenticationService;
import com.jerem.mdd.service.DefaultRegistrationService;
import com.jerem.mdd.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class used for authentication purpose.
 * <p>
 * This class implements the authentication endpoints like login and register of the application.
 * </p>
 * <p>
 * - {@link DefaultAuthenticationService} service that process the user authentication -
 * {@link UserManagementService} service used for business operations on users
 * </p>
 * 
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
@Tag(name = "AuthController", description = "Process authentication related operations")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final DefaultRegistrationService registrationService;

    public AuthController(AuthenticationService authenticationService,
            DefaultRegistrationService registrationService) {
        this.authenticationService = authenticationService;
        this.registrationService = registrationService;

    }

    /**
     * Login to the API.
     * 
     * <p>
     * This method authenticates using POST parameters and return back a Json Web Token.
     * 
     * @param {@link AuthRequestDto} the request DTO.
     * @return {@link AuthResponseDto} the response DTO.
     */

    @Operation(summary = "Login to the API",
            description = "This endpoint allows a user to authenticate by providing credentials. It returns a JWT token.")
    @ApiResponse(responseCode = "200", description = "Successful authentication, returns a token")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        try {
            AuthResponseDto authResponse = authenticationService.authenticate(request);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDto("error"));
        }
    }

    /**
     * Register to the API.
     * 
     * <p>
     * This method register using POST parameters and return back a user Summary.
     * 
     * @param {@link RegisterRequestDto} the request DTO.
     * @return {@link UserSummaryDto} the response DTO.
     */
    @Operation(summary = "Register to the API",
            description = "This endpoint allows a user to register by providing registerRequest")
    @ApiResponse(responseCode = "200", description = "Successful authentication, returns a token")
    @ApiResponse(responseCode = "409", description = "Email or username already exist")
    @PostMapping("/register")
    public ResponseEntity<UserSummaryDto> login(@RequestBody RegisterRequestDto request) {
        UserSummaryDto authResponse = registrationService.register(request);
        return ResponseEntity.ok(authResponse);
    }


}
