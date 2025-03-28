package com.jerem.mdd.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.SubscriptionDto;
import com.jerem.mdd.model.Subscription;
import com.jerem.mdd.repository.TopicRepository;
import com.jerem.mdd.repository.UserRepository;

@Component
public class SubscriptionMapper {


    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;


    public SubscriptionMapper(ModelMapper modelMapper, UserRepository userRepository,
            TopicRepository topicRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public SubscriptionDto toDto(Subscription subscription) {
        SubscriptionDto subscriptionDto = modelMapper.map(subscription, SubscriptionDto.class);
        subscriptionDto.setUserId(subscription.getUser().getId());
        subscriptionDto.setTopicId(subscription.getTopic().getId());
        return subscriptionDto;

    }

    public Subscription toEntity(SubscriptionDto subscriptionDto) {

        Subscription subscription = modelMapper.map(subscriptionDto, Subscription.class);

        subscription.setUser(userRepository.findById(subscriptionDto.getUserId())
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + subscriptionDto.getUserId())));

        subscription.setTopic(topicRepository.findById(subscriptionDto.getTopicId())
                .orElseThrow(() -> new RuntimeException(
                        "Topic not found with id: " + subscriptionDto.getTopicId())));
        return subscription;
    }


}
