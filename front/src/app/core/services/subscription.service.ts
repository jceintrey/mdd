import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subscription } from '../interfaces/subscription.interface';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  pathService = 'api/subscription'
  constructor(private httpClient: HttpClient) { }

  public subscribe(id: number): Observable<void> {
    console.log("subscribe to " + id);
    return this.httpClient.post<void>(`${this.pathService}/subscribe/${id}`, null);
  }
  public unsubscribe(id: number): Observable<void> {
    console.log("SubscriptionService.unsubscribe from " + id);
    return this.httpClient.delete<void>(`${this.pathService}/unsubscribe/${id}`);
  }

  public all(): Observable<Subscription[]> {
    console.log("SubscriptionService.all");
    return this.httpClient.get<Subscription[]>('api/subscription');
  }
}
