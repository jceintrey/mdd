package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) used when a user update their profile.
 * <p>
 * This object is sent in the request body and contains the user's credentials: username, email, and
 * password. It includes validation constraints to ensure that the provided data meets required
 * formats and security standards.
 * 
 * Please notice that password is optional but has constraints when specified
 */
@Schema(description = "Request body for user update, may containing credentials.")
@Data
@NoArgsConstructor
public class UserUpdateRequestDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    // password is optional
    @Size(min = 8, max = 32)
    private String password;
}
