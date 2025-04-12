import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../../core/interfaces/loginRequest.interface';
import { BehaviorSubject, map, Observable, tap } from 'rxjs';
import { LoginResponse } from '../../core/interfaces/loginResponse.interface';
import { Router } from '@angular/router';
import { RegisterRequest } from '../interfaces/registerRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly pathService = '/api/auth';
  private isLoggedSubject = new BehaviorSubject<boolean>(this.hasToken());
  public isLogged$ = this.isLoggedSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) { }



  isLoggedIn$() {
    return this.isLoggedSubject.asObservable();
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('jwt');
  }

  public login(credentials: LoginRequest): Observable<boolean> {
    console.log("login called");
    return this.http.post<LoginResponse>(`${this.pathService}/login`, credentials).pipe(
      tap((response) => {
        console.log("set jwt in localstorage");
        localStorage.setItem('jwt', response.token);
        this.isLoggedSubject.next(true);
      }),
      map((response) => !!response.token)
    );
  }

  public register(registerRequest: RegisterRequest): Observable<void> {
    console.log("authService register called");
    return this.http.post<void>(`${this.pathService}/register`, registerRequest);

  }

  getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  logout(): void {
    localStorage.removeItem('jwt');
    this.isLoggedSubject.next(false);
    this.router.navigate(["/landing"]);
  }
}
