

package com.jerem.mdd.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.TopicDto;
import com.jerem.mdd.exception.UserNotFoundException;
import com.jerem.mdd.mapper.TopicMapper;
import com.jerem.mdd.model.Subscription;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.TopicRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TopicService {

    private final UserManagementService userManagementService;

    private final SubscriptionService subscriptionService;
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final AuthenticationService authenticationService;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper,
            AuthenticationService authenticationService, SubscriptionService subscriptionService,
            UserManagementService userManagementService) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.authenticationService = authenticationService;
        this.subscriptionService = subscriptionService;
        this.userManagementService = userManagementService;
    }

    public Topic getTopic(@NonNull Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id " + id));
    }

    public List<TopicDto> findAll() {
        List<Topic> topics = topicRepository.findAll();


        String authenticatedUserEmail = authenticationService.getAuthenticatedUserEmail();

        User authenticatedUser = userManagementService.getUserEntityByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found",
                        "PostService.createPost"));

        Set<Long> subscriptionsTopicIds =
                subscriptionService.getSubscriptionsByUser(authenticatedUser).stream()
                        .map(sub -> sub.getTopic().getId()).collect(Collectors.toSet());

        log.debug("FindAllTopics: ");
        log.debug("User: " + authenticatedUser.getUsername());
        log.debug("subscriptionTopicsIds " + subscriptionsTopicIds);



        return topics.stream().map(topic -> {
            TopicDto topicDto = topicMapper.toDto(topic);
            topicDto.setSubscribed(subscriptionsTopicIds.contains(topic.getId()));
            return topicDto;
        }).collect(Collectors.toList());



    }
}
