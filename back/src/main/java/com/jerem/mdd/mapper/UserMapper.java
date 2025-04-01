package com.jerem.mdd.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.SubscriptionDto;
import com.jerem.mdd.dto.UserSummaryDto;
import com.jerem.mdd.dto.UserDetailedDto;
import com.jerem.mdd.model.User;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserSummaryDto toDto(User user) {
        UserSummaryDto userDto = modelMapper.map(user, UserSummaryDto.class);

        return userDto;
    }

    public User toEntity(UserSummaryDto userDto) {
        User user = modelMapper.map(userDto, User.class);


        return user;
    }


    public UserDetailedDto toUserProfileDto(User user) {
        UserDetailedDto userProfileDto = modelMapper.map(user, UserDetailedDto.class);

        List<SubscriptionDto> subscriptionDtos = user.getSubscriptions().stream().map(sub -> {
            SubscriptionDto subscriptionDto = new SubscriptionDto();
            subscriptionDto.setId(sub.getId());
            subscriptionDto.setUserId(sub.getUser().getId());
            subscriptionDto.setTopicId(sub.getTopic().getId());
            return subscriptionDto;
        }).collect(Collectors.toList());

        userProfileDto.setSubscriptions(subscriptionDtos);
        return userProfileDto;
    }

    public User toUserFromUserProfileDto(UserDetailedDto userProfileDto) {
        User user = modelMapper.map(userProfileDto, User.class);


        return user;
    }
}

