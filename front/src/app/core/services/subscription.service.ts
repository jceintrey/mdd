import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subscription } from '../interfaces/subscription.interface';


/**
 * Service responsible for handling subscription operations
 * like subscribing to a topic, unsubscribe from a topic or listing the user subscriptions
 *
 * @remarks
 * - Uses Angular’s `HttpClient` to interact with `/api/subscription` endpoints.
 */
@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  /** Base URL for subscription-related API calls. */
  pathService = 'api/subscription'
  constructor(private httpClient: HttpClient) { }

  /**
   * Subscribe to a topic given its id
   * @param id the topic identifier
   * @returns an empty Observable
   */
  public subscribe(id: number): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/subscribe/${id}`, null);
  }

  /**
   * Unsubscribe from a topic given its id
   * @param id the topic identifier
   * @returns an empty Observable
   */
  public unsubscribe(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/unsubscribe/${id}`);
  }

  /**
   * Get the subscriptions
   * @returns the subscriptions of the authenticated user
   */
  public all(): Observable<Subscription[]> {
    return this.httpClient.get<Subscription[]>('api/subscription');
  }
}
