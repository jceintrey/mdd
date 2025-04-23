import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from './core/services/auth.service';
import { BreakpointObserver } from '@angular/cdk/layout';
import { Subject, takeUntil } from 'rxjs';
import { ScreenService } from './core/services/screen.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: 'app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  title = "Mdd";
  private destroy$ = new Subject<void>();

  constructor(private observer: BreakpointObserver, private screenService: ScreenService) { }

  /**
   * Initializes the component
   * 
   *  - Emit a new value whenever the media query match status changes
   */
  ngOnInit(): void {
    this.observer
      .observe(['(max-width: 800px)'])
      .pipe(takeUntil(this.destroy$))
      .subscribe(result => {
        this.screenService.isMobile$.next(result.matches);
      });
  }

  /**
    * Cleans up any active subscriptions to prevent memory leaks.
    */
  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}