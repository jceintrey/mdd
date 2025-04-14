package com.jerem.mdd.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jerem.mdd.dto.AuthorDto;
import com.jerem.mdd.dto.PostDetailedDto;
import com.jerem.mdd.dto.PostSummaryDto;
import com.jerem.mdd.dto.TopicDetailedDto;
import com.jerem.mdd.dto.TopicSummaryDto;
import com.jerem.mdd.model.Post;
import com.jerem.mdd.service.AuthenticationService;
import com.jerem.mdd.service.SubscriptionService;
import com.jerem.mdd.service.UserManagementService;

@Component
public class PostMapper implements Mapper<Post, PostSummaryDto> {
    private final ModelMapper modelMapper;
    private final SubscriptionService subscriptionService;

    private final AuthenticationService authenticationService;

    public PostMapper(ModelMapper modelMapper, SubscriptionService subscriptionService,
            AuthenticationService authenticationService) {
        this.modelMapper = modelMapper;
        this.subscriptionService = subscriptionService;
        this.authenticationService = authenticationService;


    }

    @Override
    public PostSummaryDto toDto(Post post) {
        PostSummaryDto postDto = modelMapper.map(post, PostSummaryDto.class);

        postDto.setCreatedAt(post.getCreatedAt().toString());
        postDto.setAuthor(new AuthorDto(post.getAuthor().getId(), post.getAuthor().getUsername()));
        postDto.setTopic(new TopicSummaryDto(post.getTopic().getId(), post.getTopic().getName()));
        postDto.setContent(post.getContent());



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

        postDetailedDto
                .setAuthor(new AuthorDto(post.getAuthor().getId(), post.getAuthor().getUsername()));

        TopicDetailedDto topic =
                new TopicDetailedDto(post.getTopic().getId(), post.getTopic().getName(),
                        post.getTopic().getDescription(), subscriptionService.isSubscribed(
                                authenticationService.getAuthenticatedUser(), post.getTopic()));

        postDetailedDto.setTopic(topic);


        return postDetailedDto;

    }


}
