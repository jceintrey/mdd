package com.jerem.mdd.mapper;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jerem.mdd.dto.UserDto;
import com.jerem.mdd.model.User;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Map List<Subscription> to List<Long> with subscription Ids
        userDto.setSubscriptions(user.getSubscriptions().stream()
                .map(subscription -> subscription.getId()).collect(Collectors.toList()));

        userDto.setPosts(
                user.getPosts().stream().map(post -> post.getId()).collect(Collectors.toList()));

        userDto.setComments(user.getComments().stream().map(comment -> comment.getId())
                .collect(Collectors.toList()));

        return userDto;
    }

    public User toEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        // No mapping of subscriptions, posts and comments needed
        user.setSubscriptions(null);
        user.setPosts(null);
        user.setComments(null);

        return user;
    }
}

