import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post, PostResponse } from '../interfaces/post.interface';
import { HttpClient } from '@angular/common/http';
import { PostRequest } from '../interfaces/postRequest.interface';
import { CommentRequest } from '../interfaces/commentRequest.interface';


/**
 * Service responsible for handling posts operations
 * like new post creation, new comment, or getting post informations
 *
 * @remarks
 * - Uses Angular’s `HttpClient` to interact with `/api/posts` endpoints. 
 */
@Injectable({
  providedIn: 'root'
})
export class PostService {

  /** Base URL for post-related API calls. */
  private pathService = 'api/posts';

  constructor(private httpClient: HttpClient) { }

  /**
   * 
   * @returns an Observable that emits all posts in PostResponse object
   */
  public all(): Observable<PostResponse> {
    return this.httpClient.get<PostResponse>(this.pathService);
  }

  /**
   * Create a new post
   * @param postRequest the new post to post to API
   * @returns void
   */
  public createPost(postRequest: PostRequest): Observable<void> {
    return this.httpClient.post<void>(this.pathService, postRequest);
  }

  /**
   * Get a post by its id
   * @param id the indentifier of the expected post
   * @returns 
   */
  getPost(id: string | null): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }
  /**
   * Add a comment to the post given the post id
   * @param id the post identifier
   * @param commentRequest the comment
   * @returns the Post object with its comments
   */
  addComment(id: string, commentRequest: CommentRequest): Observable<Post> {
    return this.httpClient.post<Post>(`${this.pathService}/${id}/comments/`, commentRequest);
  }
}
