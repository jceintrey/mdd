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

/**
 * Service for managing posts and comments.
 * <p>
 * This service handles the creation of posts, retrieval of posts, and the addition of comments to a
 * post. It also interacts with repositories for data persistence and handles user authentication
 * through {@link AuthenticationService}.
 * </p>
 */
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

    /**
     * Creates a new post.
     * <p>
     * This method creates a post with the provided details and associates it with the authenticated
     * user and a specified topic.
     * </p>
     *
     * @param postRequestDto the data transfer object containing the post details
     * @return a {@link PostSummaryDto} containing the created post's summary
     * @throws TopicNotFoundException if the specified topic does not exist
     */
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

    /**
     * Retrieves all posts in a paginated format.
     * 
     * @param pageRequest the pagination parameters
     * @return a {@link Page} of {@link PostSummaryDto} representing the posts
     */
    public Page<PostSummaryDto> getAllPosts(PageRequest pageRequest) {
        Page<Post> postsPage = postRepository.findAll(pageRequest);

        return postsPage.map((post) -> postMapper.toDto(post));
    }

    /**
     * Retrieves a post by its ID.
     * <p>
     * This method returns detailed information about a post, including associated comments.
     * </p>
     *
     * @param postId the ID of the post to retrieve
     * @return a {@link PostDetailedDto} containing the detailed post information
     * @throws PostNotFoundException if the post with the specified ID does not exist
     */
    public PostDetailedDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found", "PostService.getAllPosts"));

        return postMapper.toDetailedDto(post);
    }


    /**
     * Adds a comment to an existing post.
     * <p>
     * This method creates a new comment and associates it with a specific post and the
     * authenticated user.
     * </p>
     *
     * @param postId the ID of the post to add the comment to
     * @param commentRequest the data transfer object containing the comment details
     * @return a {@link PostDetailedDto} with updated information including the new comment
     * @throws PostNotFoundException if the post with the specified ID does not exist
     */
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
