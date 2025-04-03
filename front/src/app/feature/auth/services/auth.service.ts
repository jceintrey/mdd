import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  readonly pathService = '/api/auth';
 
  constructor(private http: HttpClient) { }

 public login(credentials: LoginRequest): Observable<void> {
    return this.http.post<void>(`${this.pathService}/login`,credentials,{ withCredentials: true })
  }
}
