package com.jerem.mdd.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Exception thrown when a subscription is not found.
 */
@Slf4j
@Getter
public class SubscriptionNotFoundException extends RuntimeException {
    private String source;

    public SubscriptionNotFoundException(String message, String source) {
        super(message);
        this.source = source;
        log.error("Source: {} - Message: {}", source, message);
    }

    public SubscriptionNotFoundException(String message) {
        super(message);
        log.error("Message: {}", message);
    }
}
