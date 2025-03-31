package com.jerem.mdd.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jerem.mdd.dto.SubscriptionDetailedDto;
import com.jerem.mdd.dto.SubscriptionDto;
import com.jerem.mdd.service.DefaultAuthenticationService;
import com.jerem.mdd.service.SubscriptionService;
import com.jerem.mdd.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class used for subscription purpose.
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
@RequestMapping("/api/sub")
@Slf4j
public class SubscriptionController {

        private final SubscriptionService subscriptionService;

        public SubscriptionController(SubscriptionService subscriptionService) {
                this.subscriptionService = subscriptionService;
        }

        /**
         * Get all subscriptions.
         *
         * <p>
         * This endpoint allows an authenticated user to retrieve all their subscriptions.
         * </p>
         * 
         * @return {@link SubscriptionDetailedDto} containing detailed subscription.
         */
        @GetMapping()
        public ResponseEntity<List<SubscriptionDetailedDto>> findAll() {

                List<SubscriptionDetailedDto> subscriptionDtos = subscriptionService.findAll();
                return ResponseEntity.ok(subscriptionDtos);
        }

        /**
         * Subscribe to a topic.
         *
         * <p>
         * This endpoint allows an authenticated user to subscribe to a given topic.
         * </p>
         *
         * @param topicId the ID of the topic to subscribe to.
         * @return {@link SubscriptionDto} containing subscription.
         */
        @Operation(summary = "Subscribe to a topic",
                        description = "Allows an authenticated user to subscribe to a topic by providing its ID.",
                        responses = {@ApiResponse(responseCode = "200",
                                        description = "Successfully subscribed to the topic",
                                        content = @Content(mediaType = "application/json",
                                                        schema = @Schema(
                                                                        implementation = SubscriptionDto.class))),
                                        @ApiResponse(responseCode = "400",
                                                        description = "Bad request, invalid topic ID"),
                                        @ApiResponse(responseCode = "401",
                                                        description = "Unauthorized, user must be authenticated"),
                                        @ApiResponse(responseCode = "404",
                                                        description = "Topic not found")})
        @PostMapping("/subscribe/{topicId}")
        public ResponseEntity<SubscriptionDto> subscribe(@PathVariable("topicId") String topicId) {
                log.debug("@PostMapping(\"/subscribe/{topicId}\")");

                SubscriptionDto subscriptionDto = subscriptionService.subscribe(topicId);
                return ResponseEntity.ok(subscriptionDto);
        }

        /**
         * Unsubscribe from a topic.
         *
         * <p>
         * This endpoint allows an authenticated user to unsubscribe from a given topic.
         * </p>
         *
         * @param topicId the ID of the topic to unsubscribe from.
         * @return {@link ResponseEntity} indicating the operation result.
         */
        @Operation(summary = "Unsubscribe from a topic",
                        description = "Allows an authenticated user to unsubscribe from a topic by providing its ID.",
                        responses = {@ApiResponse(responseCode = "200",
                                        description = "Successfully unsubscribed from the topic"),
                                        @ApiResponse(responseCode = "400",
                                                        description = "Bad request, invalid topic ID"),
                                        @ApiResponse(responseCode = "401",
                                                        description = "Unauthorized, user must be authenticated"),
                                        @ApiResponse(responseCode = "404",
                                                        description = "Subscription not found")})
        @DeleteMapping("/unsubscribe/{topicId}")
        public ResponseEntity<String> unsubscribe(@PathVariable("topicId") String topicId) {
                log.debug("@DeleteMapping(\"/unsubscribe/{topicId}\")");

                subscriptionService.unsubscribe(topicId);
                return ResponseEntity.ok("Successfully unsubscribed");
        }

}
