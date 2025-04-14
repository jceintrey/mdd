package com.jerem.mdd.repository;

import com.jerem.mdd.model.Subscription;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;



public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    @Query("SELECT s FROM Subscription s JOIN FETCH s.topic WHERE s.user = :user")
    List<Subscription> findByUserWithTopics(@Param("user") User user);

    Optional<Subscription> findByUserAndTopic(User user, Topic topic);

    boolean existsByUserAndTopic(User user, Topic topic);

}
