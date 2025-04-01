package com.jerem.mdd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
