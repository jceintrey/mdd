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
    }

    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
