package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * Data Transfer Object (DTO) representing a comment.
 * 
 * <p>
 * This class is used to transfer lightweight comment information such as its content, as part of a
 * request payload in the context of post addComment endpoint.
 */
@Data
@Schema(description = "Request body used to add a comment.")
public class CommentRequestDto {

    @Size(max = 5000, message = "The comment is too long")
    private String content;

}
