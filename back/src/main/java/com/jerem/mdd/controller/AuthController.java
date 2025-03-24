package com.jerem.mdd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jerem.mdd.dto.AuthResponse;
import com.jerem.mdd.dto.LoginRequest;
import com.jerem.mdd.service.DefaultAuthenticationService;
import com.jerem.mdd.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class used for authentication purpose.
 * <p>
 * This class implements the authentication endpoints of the application.
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
    private final UserManagementService userManagementService;
    private final DefaultAuthenticationService authenticationService;

    public AuthController(DefaultAuthenticationService authenticationService,
            UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
        this.authenticationService = authenticationService;


    }

    /**
     * Login to the API.
     * 
     * <p>
     * This method authenticates using POST parameters and return back a Json Web Token.
     * 
     * @param {@link LoginRequest} the request DTO.
     * @return {@link AuthResponse} the response DTO.
     */

    @Operation(summary = "Login to the API",
            description = "This endpoint allows a user to authenticate by providing credentials. It returns a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successful authentication, returns a token"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.debug("@PostMapping(\"/login\")");
        try {
            AuthResponse authResponse = authenticationService.authenticate(request);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("error"));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        log.debug("@PostMapping(\"/login\")");
        return ResponseEntity.ok("{'status': 'ok'}");

    }
}
