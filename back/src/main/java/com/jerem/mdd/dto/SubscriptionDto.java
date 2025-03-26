
package com.jerem.mdd.dto;

import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "Response body containing the topic.")
public class SubscriptionDto {

    private Long id;

    private Long userId;

    private Long topicId;

}
