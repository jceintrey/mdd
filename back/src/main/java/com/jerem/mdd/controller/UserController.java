package com.jerem.mdd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jerem.mdd.dto.UserProfileDto;
import com.jerem.mdd.service.AuthenticationService;
import com.jerem.mdd.service.UserManagementService;
import com.jerem.mdd.service.UserProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class used for user purpose.
 * <p>
 * This class implements the user endpoints of the application.
 * </p>
 * <p>
 * - {@link UserManagementService} service for user-related operations -
 * {@link UserManagementService} service used for business operations on users
 * </p>
 * 
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
@Tag(name = "AuthController", description = "Process user related operations")
public class UserController {

    private final UserManagementService userManagementService;
    private final UserProfileService userProfileService;
    private final AuthenticationService authenticationService;

    public UserController(UserManagementService userManagementService,
            UserProfileService userProfileService, AuthenticationService authenticationService) {
        this.userManagementService = userManagementService;
        this.userProfileService = userProfileService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> status() {
        log.debug("@GetMapping(\"/me\")");

        userProfileService.getUserProfile();

        return ResponseEntity.ok(userProfileService.getUserProfile());


    }
}
