import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from 'app/core/services/auth.service';
import { Subject, takeUntil } from 'rxjs';


/**
 * The component displays a greeting page with two buton: login and register
 * @remark
 * - use `Router` to navigate to /posts page if the user is already logged in
 *
 */
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
  * - subscribe to loggedIn status
  */
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
