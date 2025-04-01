package com.jerem.mdd.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class UsernameAlreadyExistException extends RuntimeException {
    private String source;

    public UsernameAlreadyExistException(String message, String source) {
        super(message);
        this.source = source;
        log.error("Source: {} - Message: {}", source, message);

    }

    public UsernameAlreadyExistException(String message) {
        super(message);
        log.error("Message: {}", message);

    }
}
