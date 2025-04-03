import { Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, ReactiveFormsModule, Validators  } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { AuthService } from '../../services/auth.service';
import { provideHttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{

  loginForm!: FormGroup;
  public onError = false;


  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService) {}

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

   this.authService.login(LoginRequest).subscribe({
    next: () => {
      console.log('Connexion réussie');
     this.router.navigate(['']);
    },
    error: (err: any) => {
      this.onError = true;
      console.error(err);
    }

  });
  }
}
