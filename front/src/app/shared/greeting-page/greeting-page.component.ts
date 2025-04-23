import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from 'app/core/services/auth.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-greeting-page',
  imports: [RouterLink],
  templateUrl: './greeting-page.component.html',
  styleUrl: './greeting-page.component.scss'
})
export class GreetingComponent implements OnInit, OnDestroy {

  serviceError = false;
  private destroy$ = new Subject<void>();

  constructor(private authService: AuthService, private router: Router) { }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void {
    this.authService.isLoggedIn$()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.router.navigate(['/posts']);
        },
        error: () => {
          this.serviceError = true;
        }
      });
  }

}
