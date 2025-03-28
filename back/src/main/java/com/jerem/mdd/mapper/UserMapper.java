package com.jerem.mdd.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.SubscriptionDto;
import com.jerem.mdd.dto.UserBaseDto;
import com.jerem.mdd.dto.UserProfileDto;
import com.jerem.mdd.model.User;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserBaseDto toDto(User user) {
        UserBaseDto userDto = modelMapper.map(user, UserBaseDto.class);

        return userDto;
    }

    public User toEntity(UserBaseDto userDto) {
        User user = modelMapper.map(userDto, User.class);


        return user;
    }


    public UserProfileDto toUserProfileDto(User user) {
        UserProfileDto userProfileDto = modelMapper.map(user, UserProfileDto.class);

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

    public User toUserFromUserProfileDto(UserProfileDto userProfileDto) {
        User user = modelMapper.map(userProfileDto, User.class);


        return user;
    }
}

