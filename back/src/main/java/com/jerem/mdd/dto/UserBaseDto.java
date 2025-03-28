package com.jerem.mdd.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Schema(description = "DTO containing the user informations.")
@NoArgsConstructor
public class UserBaseDto {
    private Long id;

    @NonNull
    @Size(max = 30)
    private String username;

    @Email
    @Schema(description = "User email")
    private String email;


}
