package com.jerem.mdd.service;

import java.util.List;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.SubscriptionDetailedDto;
import com.jerem.mdd.dto.SubscriptionSummaryDto;
import com.jerem.mdd.exception.SubscriptionAlreadyExistException;
import com.jerem.mdd.exception.SubscriptionNotFoundException;
import com.jerem.mdd.exception.TopicNotFoundException;
import com.jerem.mdd.mapper.SubscriptionMapper;
import com.jerem.mdd.model.Subscription;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.SubscriptionRepository;
import com.jerem.mdd.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Service for managing topic subscriptions.
 * <p>
 * This service provides methods to subscribe or unsubscribe from topics, retrieve a user's
 * subscriptions, and check subscription status. It relies on the currently authenticated user
 * provided by the {@link AuthenticationService}.
 * </p>
 *
 * @see SubscriptionRepository
 * @see TopicRepository
 * @see AuthenticationService
 */
@Service
public class SubscriptionService {
        private final SubscriptionRepository subscriptionRepository;
        private final AuthenticationService authenticationService;
        private final TopicRepository topicRepository;
        private final SubscriptionMapper subscriptionMapper;

        public SubscriptionService(SubscriptionRepository subscriptionRepository,
                        AuthenticationService authenticationService,
                        TopicRepository topicRepository,
                        SubscriptionMapper subscriptionMapper) {
                this.subscriptionRepository = subscriptionRepository;
                this.authenticationService = authenticationService;
                this.topicRepository = topicRepository;
                this.subscriptionMapper = subscriptionMapper;
        }



        public List<Subscription> getSubscriptionsByUser(User user) {
                return subscriptionRepository.findByUserWithTopics(user);
        }



        public SubscriptionSummaryDto subscribe(String topicId) {
                User user = authenticationService.getAuthenticatedUser();

                Topic topic = topicRepository.findById(Long.parseLong(topicId))
                                .orElseThrow(() -> new TopicNotFoundException("Topic not found",
                                                "SubscriptionService.subscribe"));

                if (subscriptionRepository.findByUserAndTopic(user, topic).isPresent())
                        throw new SubscriptionAlreadyExistException("A subscription already exists",
                                        "SubscriptionService.subscribe");

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
                User user = authenticationService.getAuthenticatedUser();

                Topic topic = topicRepository.findById(Long.parseLong(topicId))
                                .orElseThrow(() -> new TopicNotFoundException("Topic not found",
                                                "SubscriptionService.unsubscribe"));

                Subscription subscription = subscriptionRepository.findByUserAndTopic(user, topic)
                                .orElseThrow(() -> new SubscriptionNotFoundException(
                                                "Subscription not found",
                                                "SubscriptionService.unsubscribe"));

                subscriptionRepository.delete(subscription);
        }



        /**
         * Retrieve all the authenticated user Subscriptions.
         * <p>
         * This method uses {@link authenticationService} to retrieve the authenticated user.
         * </p>
         * 
         * @return a {@link List<SubscriptionDetailedDto>>} containing the detailed subscriptions of
         *         the user
         * @throws UsernameNotFoundException if user not found in repository
         */
        public List<SubscriptionDetailedDto> findAll() {
                User user = authenticationService.getAuthenticatedUser();
                List<Subscription> subscriptions =
                                subscriptionRepository.findByUserWithTopics(user);
                return subscriptionMapper.toDetailedDto(subscriptions);

        }

        public boolean isSubscribed(User user, Topic topic) {
                return subscriptionRepository.existsByUserAndTopic(user, topic);
        }

}
