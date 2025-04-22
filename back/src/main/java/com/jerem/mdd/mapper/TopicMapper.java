package com.jerem.mdd.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.TopicDetailedDto;
import com.jerem.mdd.model.Topic;

/**
 * Mapper class used to convert {@link Topic} objects to {@link TopicDetailedDto}.
 */
@Component
public class TopicMapper implements Mapper<Topic, TopicDetailedDto> {

    private final ModelMapper modelMapper;

    public TopicMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TopicDetailedDto toDto(Topic topic) {
        TopicDetailedDto dto = modelMapper.map(topic, TopicDetailedDto.class);

        return dto;
    }

    @Override
    public Topic toEntity(TopicDetailedDto topicDto) {
        return modelMapper.map(topicDto, Topic.class);
    }

}
