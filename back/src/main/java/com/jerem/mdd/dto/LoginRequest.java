package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Request body for user login, containing credentials.")
@Data
@NoArgsConstructor

public class LoginRequest {
    private String identifier; // Peut être un email ou un username
    private String password;

    public LoginRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }
}
