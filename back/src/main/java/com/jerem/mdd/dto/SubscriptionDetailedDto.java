
package com.jerem.mdd.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response body containing the detailed Subscription.")
public class SubscriptionDetailedDto extends SubscriptionDto {


    @JsonProperty("topic_name")
    private String topicName;

    public SubscriptionDetailedDto(Long id, Long userId, Long topicId, String topicName) {
        super(id, userId, topicId);
        this.topicName = topicName;
    }
}
