package com.jerem.mdd.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.jerem.mdd.dto.CommentRequestDto;
import com.jerem.mdd.dto.PostDetailedDto;
import com.jerem.mdd.dto.PostRequestDto;
import com.jerem.mdd.dto.PostSummaryDto;
import com.jerem.mdd.mapper.PostMapper;
import com.jerem.mdd.model.Comment;

import com.jerem.mdd.model.Post;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.CommentRepository;
import com.jerem.mdd.repository.PostRepository;
import com.jerem.mdd.repository.TopicRepository;
import com.jerem.mdd.exception.*;

@Service
public class PostService {

    private final AuthenticationService authenticationService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;

    public PostService(AuthenticationService authenticationService,
            PostMapper postMapper,
            PostRepository postRepository, TopicRepository topicRepository,
            CommentRepository commentRepository) {
        this.authenticationService = authenticationService;
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
    }

    public PostSummaryDto createPost(PostRequestDto postRequestDto) {

        User authenticatedUser = authenticationService.getAuthenticatedUser();

        Topic topic = topicRepository.findById(postRequestDto.getTopicId()).orElseThrow(
                () -> new TopicNotFoundException("Topic not found", "PostService.createPost"));

        Post post = new Post();
        post.setAuthor(authenticatedUser);
        post.setCreatedAt(Date.from(Instant.now()));
        post.setTopic(topic);
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        postRepository.save(post);
        return postMapper.toDto(post);

    }

    public Page<PostSummaryDto> getAllPosts(PageRequest pageRequest) {
        Page<Post> postsPage = postRepository.findAll(pageRequest);

        return postsPage.map((post) -> postMapper.toDto(post));
    }

    public PostDetailedDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found", "PostService.getAllPosts"));

        return postMapper.toDetailedDto(post);
    }


    public PostDetailedDto addPost(Long postId, CommentRequestDto commentRequest) {
        User authenticatedUser = authenticationService.getAuthenticatedUser();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found", "PostService.getAllPosts"));

        Comment comment = new Comment();
        comment.setAuthor(authenticatedUser);
        comment.setContent(commentRequest.getContent());
        comment.setCreatedAt(Date.from(Instant.now()));
        comment.setPost(post);

        commentRepository.save(comment);
        return postMapper.toDetailedDto(post);

    }

}
