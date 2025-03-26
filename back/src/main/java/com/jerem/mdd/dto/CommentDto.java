package com.jerem.mdd.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response body containing the post.")
public class CommentDto {

    private Long id;

    private String content;

    private Date createdAt;
    private Long userId;
    private Long postId;
}
