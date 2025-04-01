package com.jerem.mdd.mapper;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.PostDetailedDto;
import com.jerem.mdd.dto.PostSummaryDto;
import com.jerem.mdd.model.Comment;
import com.jerem.mdd.model.Post;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.TopicRepository;

@Component
public class PostMapper implements Mapper<Post, PostSummaryDto> {
    private final ModelMapper modelMapper;
    private final TopicRepository topicRepository;

    public PostMapper(ModelMapper modelMapper, TopicRepository topicRepository) {
        this.modelMapper = modelMapper;
        this.topicRepository = topicRepository;
    }

    @Override
    public PostSummaryDto toDto(Post post) {
        PostSummaryDto postDto = modelMapper.map(post, PostSummaryDto.class);

        postDto.setCreatedAt(post.getCreatedAt().toString());
        postDto.setAuthorId(post.getAuthor().getId());
        postDto.setTopicId(post.getTopic().getId());



        return postDto;
    }

    public Post toEntity(PostSummaryDto postDto, User author, Topic topic, List<Comment> comments)
            throws ParseException {
        Post post = modelMapper.map(postDto, Post.class);

        DateFormat dateFormat = new SimpleDateFormat("MM/ dd/ yy");

        try {
            post.setCreatedAt(dateFormat.parse(postDto.getCreatedAt()));
        } catch (ParseException ex) {
            throw new ParseException(ex.toString(), 1);
        }

        post.setAuthor(author);
        post.setTopic(topicRepository.findById(postDto.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found")));

        return post;
    }

    @Override
    public Post toEntity(PostSummaryDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }


    public PostDetailedDto toDetailedDto(Post post) {
        PostDetailedDto postDetailedDto = modelMapper.map(post, PostDetailedDto.class);

        postDetailedDto.setCreatedAt(post.getCreatedAt().toString());
        postDetailedDto.setAuthorId(post.getAuthor().getId());
        postDetailedDto.setTopicId(post.getTopic().getId());


        return postDetailedDto;

    }


}
