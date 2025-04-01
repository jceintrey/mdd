package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request body used to add a comment.")
public class CommentRequest {

    @Size(max = 5000, message = "The comment is too long")
    private String content;

}
