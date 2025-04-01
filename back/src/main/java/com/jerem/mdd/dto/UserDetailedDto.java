package com.jerem.mdd.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserDetailedDto extends UserSummaryDto {

    private Long id;
    private List<SubscriptionDto> Subscriptions;

}
