package com.jerem.mdd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Response body containing the post.")
public class PostSummaryDto {

    private Long id;

    private String title;

    @JsonProperty("created_at")
    private String createdAt;

    private AuthorDto author;

    private TopicSummaryDto topic;

    private String content;


}
