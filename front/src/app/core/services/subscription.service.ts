import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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
    console.log("unsubscribe from " + id);
    return this.httpClient.delete<void>(`${this.pathService}/unsubscribe/${id}`);
  }
}
