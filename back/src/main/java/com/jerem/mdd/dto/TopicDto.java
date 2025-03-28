package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "Response body containing the topic.")
public class TopicDto {
    
    private Long id;

    @NonNull
    @Size(max = 30)
    private String name;

    @Size(min = 0, max = 5000)
    private String description;


}
