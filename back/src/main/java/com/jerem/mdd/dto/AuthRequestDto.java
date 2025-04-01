package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Request body for user login, containing credentials.")
@Data
@NoArgsConstructor
public class AuthRequestDto {
    @Schema(description = "The identifier can be an email or the username.", example = "jdoe")
    private String identifier;
    private String password;

    public AuthRequestDto(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }
}
