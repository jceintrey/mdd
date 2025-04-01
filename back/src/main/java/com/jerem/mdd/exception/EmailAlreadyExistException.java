package com.jerem.mdd.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class EmailAlreadyExistException extends RuntimeException {
    private String source;

    public EmailAlreadyExistException(String message, String source) {
        super(message);
        this.source = source;
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
