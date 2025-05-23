package com.jerem.mdd.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing detailed information about a post.
 * 
 * <p>
 * This class extends {@link PostSummaryDto} by including:
 * <ul>
 * <li>{@code topic} – detailed information about the topic associated with the post</li>
 * <li>{@code comments} – list of comments related to the post</li>
 * </ul>
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Response body containing the post with its content and comments.")
public class PostDetailedDto extends PostSummaryDto {

    private TopicDetailedDto topic;
    private List<CommentDto> comments;


}
