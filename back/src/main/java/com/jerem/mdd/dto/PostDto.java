package com.jerem.mdd.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Response body containing the post.")
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private String createdAt;

    private Long authorId;

    private Long topicId;

    private List<String> comments;


}
