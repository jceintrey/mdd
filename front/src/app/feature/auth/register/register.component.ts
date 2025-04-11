import { Component } from '@angular/core';

import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginRequest } from '../../../core/interfaces/loginRequest.interface';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BreakpointObserver } from '@angular/cdk/layout';
import { RegisterRequest } from 'app/core/interfaces/registerRequest.interface';
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule, MatIconModule, MatFormFieldModule, MatInputModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerForm!: FormGroup;
  isMobile = true;
  public onError = false;


  constructor(private observer: BreakpointObserver, private formBuilder: FormBuilder, private router: Router, private authService: AuthService, private snackBar: MatSnackBar) {

    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      if (screenSize.matches) {
        this.isMobile = true;
      } else {
        this.isMobile = false;
      }
    });
  }





  ngOnInit(): void {
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
          Validators.min(12)
        ]
      ]
    });
  }

  onSubmit() {

    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    const registerRequest = this.registerForm.value as RegisterRequest;

    this.authService.register(registerRequest).subscribe({
      next: () => {
        console.log("authService.register ok");
        this.router.navigate(['/login']);
        this.snackBar.open('Compte créé!', 'Fermer', { duration: 5000 });
      },
      error: (err: any) => {
        console.log("authService.register nok");
        this.snackBar.open('Erreur lors de la création du compte!', 'Fermer', { duration: 5000 });
      }
    });
  }
}
