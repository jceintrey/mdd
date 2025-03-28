package com.jerem.mdd.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response body containing the post.")
public class CommentDto {

    private Long id;

    private String content;

    private Date createdAt;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("post_id")
    private Long postId;
}
