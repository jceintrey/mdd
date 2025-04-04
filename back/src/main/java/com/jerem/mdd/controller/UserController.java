package com.jerem.mdd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jerem.mdd.dto.UserDetailedDto;
import com.jerem.mdd.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class used for user purpose.
 * <p>
 * This class implements the user endpoints of the application.
 * </p>
 * <p>
 * - {@link UserProfileService} service for the authenticated user-related operations -
 * </p>
 * 
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
@Tag(name = "UserController", description = "Process user related operations")
public class UserController {

    private final UserProfileService userProfileService;


    public UserController(UserProfileService userProfileService) {

        this.userProfileService = userProfileService;

    }

    @Operation(summary = "Get the user profile",
            description = "Allows a user to get their profile.")
    @ApiResponse(responseCode = "200", description = "Successfully get profile",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDetailedDto.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized, user must be authenticated")
    @ApiResponse(responseCode = "404", description = "Entity not found")
    @GetMapping("/me")
    public ResponseEntity<UserDetailedDto> me() {
        log.debug("Me requested");
        return ResponseEntity.ok(userProfileService.getUserProfile());
    }


}
