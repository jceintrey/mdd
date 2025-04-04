import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { BehaviorSubject, map, Observable, tap } from 'rxjs';
import { LoginResponse } from '../interfaces/loginResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  readonly pathService = '/api/auth';
  public isLogged = false;
  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);


  constructor(private http: HttpClient) { }


  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public login(credentials: LoginRequest): Observable<boolean> {
    return this.http.post<LoginResponse>(`${this.pathService}/login`, credentials).pipe(
      tap((response) => {
        localStorage.setItem('jwt', response.token);
        this.isLoggedSubject.next(true);
      }),
      map((response) => !!response.token)
    );
  }

  getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  logout(): void {
    localStorage.removeItem('jwt');
    this.isLoggedSubject.next(false);
  }
}
