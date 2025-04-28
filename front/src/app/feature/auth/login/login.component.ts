import { Component, OnDestroy, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginRequest } from '../../../core/interfaces/loginRequest.interface';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BreakpointObserver } from '@angular/cdk/layout';
import { Subject, takeUntil } from 'rxjs';
import { ScreenService } from 'app/core/services/screen.service';

/**
 * Displays the login page and enable the user to login.
 *
 * @remarks
 * - Uses `AuthService` to submit de login request
 * - Builds a reactive `FormGroup` with validation rules for identifier and password.
 */
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatIcon, MatFormFieldModule, MatInputModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})


/**
* Display a login page with a Reactive Form
*
*  @remarks
*  - use AuthService to login the submited form
*/
export class LoginComponent implements OnInit, OnDestroy {

  loginForm!: FormGroup;
  isMobile = false;
  public onError = false;
  private destroy$ = new Subject<void>();

  constructor(private screenService: ScreenService, private formBuilder: FormBuilder, private router: Router, private authService: AuthService) { }

  /**
  * Cleans up any active subscriptions to prevent memory leaks.
  */
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  /**
   * Initializes the component.
   *
   * - Sets up a breakpoint observer to track the login screen
   * - Build `loginForm`
   */
  ngOnInit(): void {

    this.screenService.isMobile$
      .pipe(takeUntil(this.destroy$))
      .subscribe(flag => this.isMobile = flag);


    this.buildForm();
  }


  /**
  * Enable the user to login to the application
  * Submit the form by calling authService.login
  */
  onSubmit() {
    const LoginRequest = this.loginForm.value as LoginRequest;

    this.authService.login(LoginRequest)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.router.navigate(['/posts']);
        },
        error: (err: any) => {
          this.onError = true;
        }

      });
  }

  /**
  * Build the form with its constraints
  */
  private buildForm() {
    this.loginForm = this.formBuilder.group({
      identifier: [
        '',
        [
          Validators.required,
        ]
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.min(12)
        ]
      ]
    });
  }
}
