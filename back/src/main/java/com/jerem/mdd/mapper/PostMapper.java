package com.jerem.mdd.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.PostDetailedDto;
import com.jerem.mdd.dto.PostSummaryDto;
import com.jerem.mdd.model.Post;

@Component
public class PostMapper implements Mapper<Post, PostSummaryDto> {
    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

    }

    @Override
    public PostSummaryDto toDto(Post post) {
        PostSummaryDto postDto = modelMapper.map(post, PostSummaryDto.class);

        postDto.setCreatedAt(post.getCreatedAt().toString());
        postDto.setAuthorId(post.getAuthor().getId());
        postDto.setTopicId(post.getTopic().getId());



        return postDto;
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
