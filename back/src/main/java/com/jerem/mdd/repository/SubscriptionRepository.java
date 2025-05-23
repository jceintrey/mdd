package com.jerem.mdd.repository;

import com.jerem.mdd.model.Subscription;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


/**
 * Repository interface for {@link Subscription} entities. Provides methods to manage user
 * subscriptions to topics.
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    /**
     * Retrieves all subscriptions for a given user.
     *
     * @param user the user whose subscriptions are to be retrieved
     * @return a list of subscriptions
     */
    @Query("SELECT s FROM Subscription s JOIN FETCH s.topic WHERE s.user = :user")
    List<Subscription> findByUserWithTopics(@Param("user") User user);

    /**
     * Finds a subscription by user and topic.
     *
     * @param user the user
     * @param topic the topic
     * @return an optional containing the subscription if it exists
     */
    Optional<Subscription> findByUserAndTopic(User user, Topic topic);

    /**
     * Checks whether a subscription exists for a given user and topic.
     *
     * @param user the user
     * @param topic the topic
     * @return true if the subscription exists, false otherwise
     */
    boolean existsByUserAndTopic(User user, Topic topic);

}
