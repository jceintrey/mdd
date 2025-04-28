import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User, UserUpdateRequest } from '../interfaces/User.interface';


/**
 * Service responsible for handling authenticated user operations
 * like retrieving or updating their profile
 *
 * @remarks
 * - Uses Angular’s `HttpClient` to interact with `/api/user/me` endpoints.
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpclient: HttpClient) { }

  /**
   * Get the user profile informations
   * @returns an Observable that emits the User object
   */
  getMe(): Observable<User> {
    return this.httpclient.get<User>('/api/user/me');
  }

  /**
   * Update the user profile
   * @param userUpdate containing the userinformations
   * @returns an Observable that emits the modified User object
   */
  update(userUpdate: UserUpdateRequest): Observable<User> {
    return this.httpclient.put<User>('/api/user', userUpdate);
  }

}
