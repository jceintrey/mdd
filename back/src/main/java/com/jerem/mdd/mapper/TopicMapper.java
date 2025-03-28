package com.jerem.mdd.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jerem.mdd.dto.TopicDto;
import com.jerem.mdd.model.Topic;

@Component

public class TopicMapper {

    private final ModelMapper modelMapper;

    public TopicMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public TopicDto toDto(Topic topic) {
        return modelMapper.map(topic, TopicDto.class);
    }

    public Topic toEntity(TopicDto topicDto) {
        return modelMapper.map(topicDto, Topic.class);
    }



}
