import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../../core/interfaces/loginRequest.interface';
import { BehaviorSubject, map, Observable, tap } from 'rxjs';
import { LoginResponse } from '../../core/interfaces/loginResponse.interface';
import { Router } from '@angular/router';
import { RegisterRequest } from '../interfaces/registerRequest.interface';

/**
 * Service responsible for handling user authentication and registration,
 * and exposing an observable for components to react to login state changes.
 *
 * @remarks
 * - Uses Angular’s `HttpClient` to interact with `/api/auth` endpoints.  
 * - Manages a `BehaviorSubject<boolean>` to broadcast the current login status.  
 * - Persists the JWT token in `localStorage` and clears it on logout.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  /** Base URL for authentication-related API calls. */
  readonly pathService = '/api/auth';
  private isLoggedSubject = new BehaviorSubject<boolean>(this.hasToken());
  constructor(private http: HttpClient, private router: Router) { }

  /**
  * Returns an observable that emits `true` when the user is logged in
  * or `false` otherwise.
  */
  isLoggedIn$() {
    return this.isLoggedSubject.asObservable();
  }

  /**
  * Checks for the presence of a JWT in localStorage.
  * @returns `true` if a token exists; `false` otherwise.
  */
  private hasToken(): boolean {
    return !!localStorage.getItem('jwt');
  }

  /**
  * Attempts to authenticate the user with given credentials.
  * @param credentials – The login payload containing identifier and password.
  * @returns An `Observable<boolean>` that emits `true` on successful login, `false` otherwise.
  * @throws HttpErrorResponse if the HTTP request fails.
  */
  public login(credentials: LoginRequest): Observable<boolean> {
    return this.http.post<LoginResponse>(`${this.pathService}/login`, credentials).pipe(
      tap((response) => {
        localStorage.setItem('jwt', response.token);
        this.isLoggedSubject.next(true);
      }),
      map((response) => !!response.token)
    );
  }

  /**
  * Registers a new user.
  * @param registerRequest – The registration payload.
  * @returns An `Observable<void>` completing when the backend confirms registration.
  * @throws HttpErrorResponse if the HTTP request fails.
  */
  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.http.post<void>(`${this.pathService}/register`, registerRequest);
  }

  /**
  * Retrieves the current JWT from localStorage.
  * @returns The JWT string, or `null` if none is stored.
  */
  getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  /**
  * Logs the user out by removing the JWT and navigating to the greeting page.
  */
  logout(): void {
    localStorage.removeItem('jwt');
    this.isLoggedSubject.next(false);
    this.router.navigate(["/greeting"]);
  }
}
