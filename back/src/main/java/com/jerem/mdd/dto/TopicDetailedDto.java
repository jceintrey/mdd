package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response body containing the topic.")
public class TopicDetailedDto extends TopicSummaryDto {

    public TopicDetailedDto(Long id, String name, String description, boolean subscribed) {
        super(id, name);
        this.description = description;
        this.subscribed = subscribed;
    }

    @Size(min = 0, max = 5000)
    private String description;

    private boolean subscribed;


}
