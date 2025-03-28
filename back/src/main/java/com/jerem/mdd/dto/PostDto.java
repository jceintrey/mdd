package com.jerem.mdd.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Response body containing the post.")
public class PostDto {

    private Long id;

    private String title;

    private String content;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("topic_id")
    private Long topicId;

    private List<String> comments;


}
