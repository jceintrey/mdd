import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Topic } from '../interfaces/topic.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private pathService = 'api/topic';


  constructor(private httpClient: HttpClient) { }


  public all(): Observable<Topic[]> {
    console.log("call topicService.all")
    return this.httpClient.get<Topic[]>(this.pathService);
  }

}
