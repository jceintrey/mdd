import { BreakpointObserver } from '@angular/cdk/layout';
import { NgClass } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { MatNavList } from '@angular/material/list';
import { MatSidenav, MatSidenavContainer, MatSidenavContent } from '@angular/material/sidenav';
import { MatToolbar, MatToolbarRow } from '@angular/material/toolbar';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { Observable, Subject, takeUntil } from 'rxjs';
import { ScreenService } from 'app/core/services/screen.service';

@Component({
  selector: 'app-layout',
  imports: [RouterOutlet, RouterLinkActive, RouterLink, MatSidenav, MatSidenavContent, MatSidenavContainer, MatIcon, NgClass, MatNavList, MatToolbar, MatToolbarRow],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {
  title = 'material-responsive-sidenav';
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  isMobile = false;
  isCollapsed = true;
  private destroy$ = new Subject<void>();

  constructor(private screenService: ScreenService, private router: Router, private authService: AuthService) { }

  ngOnInit() {
    this.screenService.isMobile$
      .pipe(takeUntil(this.destroy$))
      .subscribe(flag => this.isMobile = flag);
  }


  toggleMenu() {
    if (this.isMobile) {
      this.sidenav.toggle();
      this.isCollapsed = false;
    } else {
      this.sidenav.open();
      this.isCollapsed = !this.isCollapsed;
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/greeting']);
  }


}
