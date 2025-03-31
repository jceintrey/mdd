package com.jerem.mdd.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jerem.mdd.dto.TopicDto;
import com.jerem.mdd.service.TopicService;
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
        @GetMapping()
        public ResponseEntity<List<TopicDto>> findAll() {

                List<TopicDto> topicDtos = topicService.findAll();
                return ResponseEntity.ok(topicDtos);
        }



}
