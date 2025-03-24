package com.jerem.mdd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Schema(description = "Response body containing the authenticated user informations.")
public class UserDto {
    private Long id;

    @Email
    @Schema(description = "User email")
    private String email;

    @NonNull
    @Size(max = 30)
    private String username;


    @Size(max = 120)
    private String password;

}
