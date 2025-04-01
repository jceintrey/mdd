package com.jerem.mdd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request body used to create a post.")
public class PostRequestDto {
    private String title;

    @JsonProperty("topic_id")
    private Long topicId;

    private String content;
}
