import { Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BreakpointObserver } from '@angular/cdk/layout';
import { RegisterRequest } from 'app/core/interfaces/registerRequest.interface';
import { MatSnackBar } from "@angular/material/snack-bar";
import { OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ScreenService } from 'app/core/services/screen.service';
import { passwordComplexityValidator } from 'app/core/validators/password.validator';

/**
 * Displays the register page.
 *
 * @remarks
 * - Uses `AuthService` to submit the register request
 * - Builds a reactive `FormGroup` with validation constraints
 * - Uses `MatSnackBar` to popup the user on registration error
 */
@Component({
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule, MatIconModule, MatFormFieldModule, MatInputModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit, OnDestroy {
  registerForm!: FormGroup;
  isMobile = true;
  public onError = false;
  private destroy$ = new Subject<void>();

  constructor(private screenService: ScreenService, private formBuilder: FormBuilder, private router: Router, private authService: AuthService, private snackBar: MatSnackBar) {


  }

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
  * - Build `registerForm`
  */
  ngOnInit(): void {

    this.screenService.isMobile$
      .pipe(takeUntil(this.destroy$))
      .subscribe(flag => this.isMobile = flag);

    this.buildForm();
  }


  /**
  * Enable a new user to register to tje application
  * Submit the form by calling authService.register
  */
  onSubmit() {

    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    const registerRequest = this.registerForm.value as RegisterRequest;

    this.authService.register(registerRequest)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.router.navigate(['/login']);
          this.snackBar.open('Compte créé!', 'Fermer', { duration: 5000 });
        },
        error: (err: any) => {
          this.snackBar.open('Erreur lors de la création du compte!', 'Fermer', { duration: 5000 });
        }
      });
  }

  /**
  * Build the form with its constraints
  */
  private buildForm() {
    this.registerForm = this.formBuilder.group({
      username: [
        '',
        [
          Validators.required,
        ]
      ],
      email: [
        '',
        [
          Validators.required,
          Validators.email,
        ]
      ],
      password: [
        '',
        [
          Validators.required,
          passwordComplexityValidator
        ]
      ]
    });
  }

}
