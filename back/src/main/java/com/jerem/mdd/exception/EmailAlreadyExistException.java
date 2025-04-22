package com.jerem.mdd.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * Exception thrown when a the email is already taken by another User
 */
@Slf4j
@Getter
public class EmailAlreadyExistException extends RuntimeException {
    private String source;

    public EmailAlreadyExistException(String message, String source) {
        super(message);
        this.source = source;
        log.error("Source: {} - Message: {}", source, message);

    }

    public EmailAlreadyExistException(String message) {
        super(message);
        log.error("Message: {}", message);

    }
}
