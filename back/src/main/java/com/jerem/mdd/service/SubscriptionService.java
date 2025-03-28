package com.jerem.mdd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jerem.mdd.model.Subscription;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.SubscriptionRepository;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }



    public List<Subscription> getSubscriptionsByUser(User user) {
        return subscriptionRepository.findByUserWithTopics(user);
    }
}
