import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post, PostResponse } from '../interfaces/post.interface';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostService {


  private pathService = 'api/posts';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<PostResponse> {
    return this.httpClient.get<PostResponse>(this.pathService);
  }


}
