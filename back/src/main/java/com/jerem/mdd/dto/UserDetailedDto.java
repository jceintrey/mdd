package com.jerem.mdd.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) givving detailed user informations.
 * <p>
 * This object contains extends {@link UserSummaryDto} and adds a description and a list of
 * {@SubscriptionSummaryDto}.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDetailedDto extends UserSummaryDto {

    private Long id;

    private List<SubscriptionSummaryDto> Subscriptions;

}
