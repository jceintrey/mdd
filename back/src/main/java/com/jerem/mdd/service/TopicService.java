

package com.jerem.mdd.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.TopicDto;
import com.jerem.mdd.mapper.TopicMapper;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.repository.TopicRepository;

import lombok.NonNull;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    public Topic getTopic(@NonNull Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id " + id));
    }

    public List<TopicDto> findAll() {
        List<Topic> topics = topicRepository.findAll();
        return topicMapper.toDto(topics);
    }
}
