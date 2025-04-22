package com.jerem.mdd.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.SubscriptionDetailedDto;
import com.jerem.mdd.dto.SubscriptionSummaryDto;
import com.jerem.mdd.model.Subscription;
import com.jerem.mdd.repository.TopicRepository;
import com.jerem.mdd.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;


/**
 * Mapper class used to convert {@link Subscription} objects to {@link SubscriptionSummaryDto}. It
 * uses {@link UserRepository} and {@TopicRepository} to get the user and topic from their ids.
 */
@Component
public class SubscriptionMapper implements Mapper<Subscription, SubscriptionSummaryDto> {


    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;


    public SubscriptionMapper(ModelMapper modelMapper, UserRepository userRepository,
            TopicRepository topicRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public SubscriptionSummaryDto toDto(Subscription subscription) {
        SubscriptionSummaryDto subscriptionDto =
                modelMapper.map(subscription, SubscriptionSummaryDto.class);
        subscriptionDto.setUserId(subscription.getUser().getId());
        subscriptionDto.setTopicId(subscription.getTopic().getId());
        return subscriptionDto;

    }

    @Override
    public Subscription toEntity(SubscriptionSummaryDto subscriptionDto) {

        Subscription subscription = modelMapper.map(subscriptionDto, Subscription.class);

        subscription.setUser(userRepository.findById(subscriptionDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + subscriptionDto.getUserId())));

        subscription.setTopic(topicRepository.findById(subscriptionDto.getTopicId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Topic not found with id: " + subscriptionDto.getTopicId())));
        return subscription;
    }


    public SubscriptionDetailedDto toDetailedDto(Subscription subscription) {
        return new SubscriptionDetailedDto(subscription.getId(), subscription.getUser().getId(),
                subscription.getTopic().getId(), subscription.getTopic().getName());
    }

    public List<SubscriptionDetailedDto> toDetailedDto(List<Subscription> subscriptions) {
        return subscriptions.stream().map(this::toDetailedDto).collect(Collectors.toList());
    }

}
