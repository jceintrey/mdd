

package com.jerem.mdd.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.TopicDetailedDto;
import com.jerem.mdd.mapper.TopicMapper;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.TopicRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


/**
 * Service for managing topics.
 * <p>
 * Provides operations to retrieve topics and determine whether the authenticated user is subscribed
 * to them. This service collaborates with {@link AuthenticationService} and
 * {@link SubscriptionService} to enrich topic data with user-specific information.
 * </p>
 *
 * @see TopicRepository
 * @see TopicMapper
 * @see SubscriptionService
 * @see AuthenticationService
 */
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

    /**
     * Retrieves a topic by its ID.
     * <p>
     * This method is typically used when details of a single topic are required, for example when
     * viewing or editing a specific topic.
     * </p>
     *
     * @param id the ID of the topic to retrieve
     * @return the {@link Topic} corresponding to the given ID
     * @throws RuntimeException if no topic is found with the provided ID
     */
    public Topic getTopic(@NonNull Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id " + id));
    }

    /**
     * Retrieves all topics in the system along with the subscription status for the authenticated
     * user.
     * <p>
     * Each topic is mapped to a {@link TopicDetailedDto}, enriched with a flag indicating whether
     * the current authenticated user is subscribed to it.
     * </p>
     *
     * @return a list of {@link TopicDetailedDto} objects representing all topics
     * @throws UsernameNotFoundException if the user is not found in the security context
     */
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
