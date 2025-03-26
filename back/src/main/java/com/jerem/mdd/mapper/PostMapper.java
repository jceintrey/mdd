package com.jerem.mdd.mapper;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jerem.mdd.dto.PostDto;
import com.jerem.mdd.model.Comment;
import com.jerem.mdd.model.Post;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.TopicRepository;

@Component
public class PostMapper {
    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper, TopicRepository topicRepository) {
        this.modelMapper = modelMapper;
    }

    public PostDto toDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);

        postDto.setCreatedAt(post.getCreatedAt().toString());
        postDto.setAuthorId(post.getAuthor().getId());
        postDto.setTopicId(post.getTopic().getId());
        postDto.setComments(post.getComments().stream().map((c) -> c.getId().toString())
                .collect(Collectors.toList()));

        return postDto;
    }

    public Post toEntity(PostDto postDto, User author, Topic topic, List<Comment> comments)
            throws ParseException {
        Post post = modelMapper.map(postDto, Post.class);

        DateFormat DFormat = new SimpleDateFormat("MM/ dd/ yy");

        try {
            post.setCreatedAt(DFormat.parse(postDto.getCreatedAt()));
        } catch (ParseException ex) {
            throw new ParseException(ex.toString(), 1);
        }

        post.setAuthor(author);
        post.setTopic(topicRepository.findById(postDto.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found")));

        return post;
    }

}
