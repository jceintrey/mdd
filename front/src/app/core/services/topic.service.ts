import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Topic } from '../interfaces/topic.interface';
import { Observable } from 'rxjs';

/**
 * Service responsible for handling topic operations
 * like retrieving topics
 *
 * @remarks
 * - Uses Angular’s `HttpClient` to interact with `/api/topic` endpoints.
 */
@Injectable({
  providedIn: 'root'
})
export class TopicService {
  /** Base URL for topic-related API calls. */
  private pathService = 'api/topic';

  constructor(private httpClient: HttpClient) { }

  /**
    * Get the topics
    * @returns the topics available
    */
  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.pathService);
  }

}
