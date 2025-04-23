import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from 'app/core/services/auth.service';

@Component({
  selector: 'app-greeting-page',
  imports: [RouterLink],
  templateUrl: './greeting-page.component.html',
  styleUrl: './greeting-page.component.scss'
})
export class GreetingComponent implements OnInit {

  serviceError = false;
  constructor(private authService: AuthService, private router: Router) { }
  ngOnInit(): void {
    this.authService.isLoggedIn$().subscribe({
      next: () => {
        this.router.navigate(['/posts']);
      },
      error: () => {
        this.serviceError = true;
      }
    });
  }


}
