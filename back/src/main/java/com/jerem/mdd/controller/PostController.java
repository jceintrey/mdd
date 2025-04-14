package com.jerem.mdd.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jerem.mdd.dto.CommentRequestDto;
import com.jerem.mdd.dto.PostDetailedDto;
import com.jerem.mdd.dto.PostRequestDto;
import com.jerem.mdd.dto.PostSummaryDto;
import com.jerem.mdd.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class used for post purpose.
 * <p>
 * This class implements the post endpoints like create, get a pagined list of the user posts,..
 * </p>
 * <p>
 * </p>
 * 
 */
@Slf4j
@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Endpoints for managing posts")
public class PostController {

        private final PostService postService;

        public PostController(PostService postService) {
                this.postService = postService;
        }


        /**
         * Create a post.
         *
         * @param postDto DTO containing post details.
         * @return Created post.
         */
        @Operation(summary = "Create a post", description = "Allows a user to create a new post.")
        @ApiResponse(responseCode = "201", description = "Post created successfully")
        @ApiResponse(responseCode = "401", description = "Unauthorized")
        @ApiResponse(responseCode = "404", description = "User or topic not found")
        @PostMapping
        public ResponseEntity<PostSummaryDto> createPost(
                        @Valid @RequestBody PostRequestDto postRequestDto) {
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(postService.createPost(postRequestDto));

        }

        /**
         * Get a paginated list of posts.
         *
         * @param page Page number (default 0).
         * @param size Page size (default 10).
         * @return Paginated list of posts.
         */
        @Operation(summary = "Get all posts", description = "Retrieve a paginated list of posts.")
        @ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
        @GetMapping
        public ResponseEntity<Page<PostSummaryDto>> getAllPosts(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {
                return ResponseEntity.ok(postService.getAllPosts(PageRequest.of(page, size)));
        }

        /**
         * Get a post by its ID.
         *
         * @param postId Post ID.
         * @return Post details.
         */
        @Operation(summary = "Get post by ID",
                        description = "Retrieve a specific post by its ID with its content.")
        @ApiResponse(responseCode = "200", description = "Post found")
        @ApiResponse(responseCode = "400", description = "Bad Request")
        @ApiResponse(responseCode = "404", description = "Post not found")

        @GetMapping("/{postId}")
        public ResponseEntity<PostDetailedDto> getPostById(@PathVariable Long postId) {
                return ResponseEntity.ok(postService.getPostById(postId));
        }



        /**
         * Delete a post by its ID.
         *
         * @param postId Post ID.
         */
        @Operation(summary = "Delete post", description = "Delete a post by its ID.")
        @ApiResponse(responseCode = "204", description = "Post deleted successfully")
        @ApiResponse(responseCode = "404", description = "Post not found")
        @DeleteMapping("/{postId}")
        public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
                postService.deletePost(postId);
                return ResponseEntity.noContent().build();
        }

        /**
         * Add a comment to a post.
         *
         * @param postId Post ID.
         * @return Post details.
         */
        @Operation(summary = "Add a comment",
                        description = "Add a comment to a Post given the postId")
        @ApiResponse(responseCode = "201", description = "Comment added successfully")
        @ApiResponse(responseCode = "400", description = "Bad Request")
        @ApiResponse(responseCode = "404", description = "Post not found")

        @PostMapping("/{postId}/comments/")
        public ResponseEntity<PostDetailedDto> addComment(@PathVariable Long postId,
                        @Valid @RequestBody CommentRequestDto commentRequest) {
                log.debug("AuthController.addComment");
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(postService.addPost(postId, commentRequest));

        }
}
