package com.jerem.mdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response body containing the topic.")
public class TopicDto {

    private Long id;

    @NonNull
    @Size(max = 30)
    private String name;

    @Size(min = 0, max = 5000)
    private String description;

    private boolean subscribed;


}
