import { BreakpointObserver } from '@angular/cdk/layout';
import { NgClass } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { MatNavList } from '@angular/material/list';
import { MatSidenav, MatSidenavContainer, MatSidenavContent } from '@angular/material/sidenav';
import { MatToolbar, MatToolbarRow } from '@angular/material/toolbar';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { Observable, Subject, takeUntil } from 'rxjs';
import { ScreenService } from 'app/core/services/screen.service';


/**
 * Component displaying the menu layout of the application
 * @remark
 * - Use `ScreenService` to set isMobile flag used in template to manage the mobile and pc view
 * - Use `AuthService` to call logout method on logout
 * - Use `Router` and `RouterLink` for navigate operations
 * 
 */
@Component({
  selector: 'app-layout',
  imports: [RouterOutlet, RouterLinkActive, RouterLink, MatSidenav, MatSidenavContent, MatSidenavContainer, MatIcon, NgClass, MatNavList, MatToolbar, MatToolbarRow],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent implements OnInit, OnDestroy {
  title = 'material-responsive-sidenav';
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  isMobile = false;
  isCollapsed = true;
  private destroy$ = new Subject<void>();

  constructor(private screenService: ScreenService, private router: Router, private authService: AuthService) { }


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
  * - get the isMobile status
  */
  ngOnInit() {
    this.screenService.isMobile$
      .pipe(takeUntil(this.destroy$))
      .subscribe(flag => this.isMobile = flag);
  }

  /**
   * Used to open the sidenav
   */
  toggleMenu() {
    if (this.isMobile) {
      this.sidenav.toggle();
      this.isCollapsed = false;
    } else {
      this.sidenav.open();
      this.isCollapsed = !this.isCollapsed;
    }
  }
  /**
   * Logout and redirect the user to greeting page on logout
   */
  logout() {
    this.authService.logout();
    this.router.navigate(['/greeting']);
  }


}
