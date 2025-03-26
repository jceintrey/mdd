package com.jerem.mdd.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "Response body containing the authenticated user informations.")
public class UserDto {
    private Long id;

    @NonNull
    @Size(max = 30)
    private String username;

    @Email
    @Schema(description = "User email")
    private String email;

    @Size(max = 120)
    private String password;

    private List<Long> subscriptions;

    private List<Long> posts;

    private List<Long> comments;

}
