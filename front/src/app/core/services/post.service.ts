import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post, PostResponse } from '../interfaces/post.interface';
import { HttpClient } from '@angular/common/http';
import { PostRequest } from '../interfaces/postRequest.interface';
import { CommentRequest } from '../interfaces/commentRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class PostService {


  private pathService = 'api/posts';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<PostResponse> {
    return this.httpClient.get<PostResponse>(this.pathService);
  }

  public createPost(postRequest: PostRequest): Observable<void> {
    return this.httpClient.post<void>(this.pathService, postRequest);
  }

  getPost(id: string | null): Observable<Post> {
    console.log("get post " + id)
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  addComment(id: string, commentRequest: CommentRequest): Observable<Post> {
    console.log("try to add a comment");
    return this.httpClient.post<Post>(`${this.pathService}/${id}/comments/`, commentRequest);
  }
}
