package com.jerem.mdd.dto;

import java.util.List;
import com.jerem.mdd.model.Subscriptions;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    @Email
    @Schema(description = "User email")
    private String email;

    @NonNull
    @Size(max = 30)
    private String username;

    private List<Subscriptions> Subscriptions;



}
