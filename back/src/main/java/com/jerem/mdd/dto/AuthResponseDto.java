package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Response body containing the JSON Web Token used for API authentication.")
@Data
@AllArgsConstructor
public class AuthResponseDto {
    @Schema(description = "The JSON Web Token used for authentication.",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYm9iQG1haWwudGxkIiwiZXhwIjoxNzM4MzQzMTk1LCJpYXQiOjE3MzgzMzk1OTUsInJvbGVzIjoiVVNFUiJ9.dZccscSkZYawnt40cVRFF-3ds5BO7p5yhMH_Syuofrs")
    private String token;
}
