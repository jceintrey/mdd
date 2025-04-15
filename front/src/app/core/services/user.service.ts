import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User, UserUpdateRequest } from '../interfaces/User.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  constructor(private httpclient: HttpClient) { }

  getMe(): Observable<User> {
    return this.httpclient.get<User>('/api/user/me');
  }

  update(userUpdate: UserUpdateRequest): Observable<User> {
    return this.httpclient.put<User>('/api/user', userUpdate);
  }

}
