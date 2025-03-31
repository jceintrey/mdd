package com.jerem.mdd.service;

import java.util.List;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.SubscriptionDetailedDto;
import com.jerem.mdd.dto.SubscriptionDto;
import com.jerem.mdd.mapper.SubscriptionMapper;
import com.jerem.mdd.model.Subscription;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.SubscriptionRepository;
import com.jerem.mdd.repository.TopicRepository;
import com.jerem.mdd.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final AuthenticationService authenticationService;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
            AuthenticationService authenticationService, TopicRepository topicRepository,
            UserRepository userRepository, SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.authenticationService = authenticationService;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.subscriptionMapper = subscriptionMapper;
    }



    public List<Subscription> getSubscriptionsByUser(User user) {
        return subscriptionRepository.findByUserWithTopics(user);
    }



    public SubscriptionDto subscribe(String topicId) {
        String username = authenticationService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Topic topic = topicRepository.findById(Long.parseLong(topicId))
                .orElseThrow(() -> new EntityNotFoundException("Topic not found"));

        if (subscriptionRepository.findByUserAndTopic(user, topic).isPresent())
            throw new IllegalArgumentException("A subscription already exists");

        Subscription subscription = new Subscription(user, topic);

        subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(subscription);

    }


    /**
     * Remove the subscription if it exists for the given topicId and the authenticated user.
     *
     * @param topicId the ID of the topic to unsubscribe from.
     * @throws UsernameNotFoundException if the user is not found.
     * @throws EntityNotFoundException if the subscription does not exist.
     */
    public void unsubscribe(String topicId) {
        String username = authenticationService.getAuthenticatedUserEmail();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Topic topic = topicRepository.findById(Long.parseLong(topicId))
                .orElseThrow(() -> new EntityNotFoundException("Topic not found"));

        // On récupère directement la subscription spécifique
        Subscription subscription = subscriptionRepository.findByUserAndTopic(user, topic)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found"));

        subscriptionRepository.delete(subscription);
    }



    /**
     * Retrieve all the authenticated user Subscriptions.
     * <p>
     * This method uses {@link authenticationService} to retrieve the authenticated user.
     * </p>
     * 
     * @return a {@link List<SubscriptionDetailedDto>>} containing the detailed subscriptions of the
     *         user
     * @throws UsernameNotFoundException if user not found in repository
     */
    public List<SubscriptionDetailedDto> findAll() {
        String username = authenticationService.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Subscription> subscriptions = subscriptionRepository.findByUserWithTopics(user);
        return subscriptionMapper.toDetailedDto(subscriptions);

    }


}
