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


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatIcon, MatFormFieldModule, MatInputModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm!: FormGroup;
  isMobile = true;
  public onError = false;
  private destroy$ = new Subject<void>();

  constructor(private observer: BreakpointObserver, private formBuilder: FormBuilder, private router: Router, private authService: AuthService) {

    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      if (screenSize.matches) {
        this.isMobile = true;
      } else {
        this.isMobile = false;
      }
    });
  }
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }


  ngOnInit(): void {
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

  onSubmit() {
    const LoginRequest = this.loginForm.value as LoginRequest;

    this.authService.login(LoginRequest)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          console.log("authService.login ok");
          this.router.navigate(['/posts']);
        },
        error: (err: any) => {
          console.log("authService.login nok");
          this.onError = true;
          console.error(err);
        }

      });
  }
}
