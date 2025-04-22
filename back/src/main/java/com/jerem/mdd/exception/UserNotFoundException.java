package com.jerem.mdd.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Exception thrown when a user is not found.
 */
@Slf4j
@Getter
public class UserNotFoundException extends RuntimeException {
    private String source;

    public UserNotFoundException(String message, String source) {
        super(message);
        this.source = source;
        log.error("Source: {} - Message: {}", source, message);

    }

    public UserNotFoundException(String message) {
        super(message);
        log.error("Message: {}", message);

    }
}
