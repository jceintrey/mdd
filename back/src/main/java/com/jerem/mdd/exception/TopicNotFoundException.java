package com.jerem.mdd.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class TopicNotFoundException extends RuntimeException {
    private String source;

    public TopicNotFoundException(String message, String source) {
        super(message);
        this.source = source;
        log.error("Source: {} - Message: {}", source, message);
    }

    public TopicNotFoundException(String message) {
        super(message);
        log.error("Message: {}", message);
    }
}
