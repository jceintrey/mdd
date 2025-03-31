package com.jerem.mdd.mapper;


import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.TopicDto;
import com.jerem.mdd.model.Topic;

/**
 * Mapper component for converting between {@link Topic} entities and {@link TopicDto} DTOs.
 * <p>
 * This class provides utility methods to map {@link Topic} objects to {@link TopicDto} and vice
 * versa, using {@link ModelMapper} for automated mapping.
 * </p>
 */
@Component
public class TopicMapper implements Mapper<Topic, TopicDto> {

    private final ModelMapper modelMapper;

    public TopicMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TopicDto toDto(Topic topic) {
        return modelMapper.map(topic, TopicDto.class);
    }

    @Override
    public Topic toEntity(TopicDto topicDto) {
        return modelMapper.map(topicDto, Topic.class);
    }

    @Override
    public List<TopicDto> toDto(List<Topic> topics) {
        return topics.stream().map(this::toDto).collect(Collectors.toList());
    }



}
