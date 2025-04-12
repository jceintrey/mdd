package com.jerem.mdd.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jerem.mdd.dto.TopicDto;
import com.jerem.mdd.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class used for topic purpose.
 * <p>
 * This class implements the topic endpoints of the application.
 * </p>
 * <p>
 * </p>
 * 
 */
@RestController
@RequestMapping("/api/topic")
@Slf4j
@Tag(name = "TopicController", description = "Process topic related operations")
public class TopicController {

        private final TopicService topicService;

        public TopicController(TopicService topicService) {
                this.topicService = topicService;
        }

        /**
         * Get all topics.
         *
         * <p>
         * This endpoint allows an authenticated user to list all existing topics.
         * </p>
         * 
         * @return {@link TopicDto} containing detailed subscription.
         */
        @Operation(summary = "Get all topics", description = "Allows a user to get the topics.")
        @ApiResponse(responseCode = "200", description = "Successfully get topics",
                        content = @Content(mediaType = "application/json", array = @ArraySchema(
                                        schema = @Schema(implementation = TopicDto.class))))
        @ApiResponse(responseCode = "401", description = "Unauthorized, user must be authenticated")
        @ApiResponse(responseCode = "404", description = "Entity not found")
        @GetMapping()
        public ResponseEntity<List<TopicDto>> findAll() {
                log.debug("TopicController findAll");
                List<TopicDto> topicDtos = topicService.findAll();
                return ResponseEntity.ok(topicDtos);
        }



}
