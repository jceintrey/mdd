package com.jerem.mdd.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.TopicDetailedDto;
import com.jerem.mdd.model.Topic;

/**
 * Mapper component for converting between {@link Topic} entities and {@link TopicDetailedDto} DTOs.
 * <p>
 * This class provides utility methods to map {@link Topic} objects to {@link TopicDetailedDto} and
 * vice versa, using {@link ModelMapper} for automated mapping.
 * </p>
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


        boolean subscribed = checkIfUserIsSubscribed(topic);
        dto.setSubscribed(subscribed);


        return dto;
    }

    @Override
    public Topic toEntity(TopicDetailedDto topicDto) {
        return modelMapper.map(topicDto, Topic.class);
    }


    private boolean checkIfUserIsSubscribed(Topic topic) {
        // Logique pour savoir si l'utilisateur est abonné à ce topic
        // Par exemple, vérifier dans une base de données ou un service externe
        return false; // Exemple par défaut
    }

}
