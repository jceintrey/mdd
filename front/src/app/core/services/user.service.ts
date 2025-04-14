import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/User.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpclient: HttpClient) { }

  getMe(): Observable<User> {
    return this.httpclient.get<User>('/api/user/me');
  }

}
