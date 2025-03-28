
package com.jerem.mdd.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response body containing the topic.")
public class SubscriptionDto {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("topic_id")
    private Long topicId;

}
