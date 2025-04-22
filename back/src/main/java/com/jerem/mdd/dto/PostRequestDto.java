package com.jerem.mdd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * Data Transfer Object (DTO) representing a post request.
 * 
 * <p>
 * This class is used to transfer lightweight post information such as title, topicID and the post
 * content, as part of a request payload in the context of post create endpoint.
 */
@Data
@Schema(description = "Request body used to create a post.")
public class PostRequestDto {
    private String title;

    @JsonProperty("topic_id")
    private Long topicId;

    @NotBlank
    @Size(max = 50000, message = "The post is too long")
    private String content;
}
