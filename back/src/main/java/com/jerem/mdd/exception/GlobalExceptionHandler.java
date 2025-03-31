package com.jerem.mdd.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.jerem.mdd.dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Global Exception Handler that listen for exceptions thrown from the application It returns
 * ResponseEntity in a standardized response format.
 * 
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(ex.getMessage(), ex.getSource()));
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCommentNotFoundException(
            CommentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(ex.getMessage(), ex.getSource()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlePostNotFoundException(PostNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(ex.getMessage(), ex.getSource()));
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleSubscriptionNotFoundException(
            SubscriptionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(ex.getMessage(), ex.getSource()));
    }

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTopicNotFoundException(
            TopicNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(ex.getMessage(), ex.getSource()));
    }

    @ExceptionHandler(SubscriptionAlreadyExistException.class)
    public ResponseEntity<ApiErrorResponse> handleSubscriptionAlreadyExistException(
            SubscriptionAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(ex.getMessage(), ex.getSource()));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiErrorResponse> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse("Erreur conversion en nombre: ", ex.getMessage()));
    }
}
