

package com.jerem.mdd.service;

import org.springframework.stereotype.Service;

import com.jerem.mdd.model.Topic;
import com.jerem.mdd.repository.TopicRepository;

import lombok.NonNull;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic getTopic(@NonNull Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id " + id));
    }
}
