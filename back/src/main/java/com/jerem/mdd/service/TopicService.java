

package com.jerem.mdd.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.TopicDetailedDto;
import com.jerem.mdd.mapper.TopicMapper;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.TopicRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TopicService {


    private final SubscriptionService subscriptionService;
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final AuthenticationService authenticationService;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper,
            AuthenticationService authenticationService, SubscriptionService subscriptionService) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.authenticationService = authenticationService;
        this.subscriptionService = subscriptionService;
    }

    public Topic getTopic(@NonNull Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id " + id));
    }

    public List<TopicDetailedDto> findAll() {
        List<Topic> topics = topicRepository.findAll();

        User authenticatedUser = authenticationService.getAuthenticatedUser();

        return topics.stream().map(topic -> {
            TopicDetailedDto topicDto = topicMapper.toDto(topic);
            topicDto.setSubscribed(subscriptionService.isSubscribed(authenticatedUser, topic));
            return topicDto;
        }).collect(Collectors.toList());

    }

}
