package com.jerem.mdd.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Body containing a comment.")
public class CommentDto {

    private Long id;

    private String content;

    @JsonProperty("created_at")
    private Date createdAt;

    private AuthorDto author;

    @JsonProperty("post_id")
    private Long postId;
}
