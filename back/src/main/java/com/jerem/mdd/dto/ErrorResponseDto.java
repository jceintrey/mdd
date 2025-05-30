package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for error responses.
 * <p>
 * Represents a generic response body for api errors.
 * </p>
 */
@Data
@AllArgsConstructor
@Schema(description = "Body containing a standardized error response.")
public class ErrorResponseDto {

    private String description;

    @Schema(description = "source that throws the error",
            example = "UserManagementService.getUserbyId")
    private String source;

    public ErrorResponseDto(String message) {
        this.description = message;
    }

}
