package com.jerem.mdd.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Response body containing the post with its content and comments.")
public class PostDetailedDto extends PostSummaryDto {


    private TopicDetailedDto topic;
    private List<CommentDto> comments;


}
