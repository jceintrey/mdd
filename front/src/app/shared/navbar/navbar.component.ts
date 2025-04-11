import { Component, Input, ViewChild } from '@angular/core';
import { MatToolbar, MatToolbarModule, MatToolbarRow } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatSidenav, MatSidenavContainer, MatSidenavContent, MatSidenavModule } from '@angular/material/sidenav';
import { MatListItem, MatListModule, MatNavList } from '@angular/material/list';
import { Router, RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { map, Observable, shareReplay, Subscription } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';
import { AsyncPipe, NgClass } from '@angular/common';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';


@Component({
  selector: 'app-navbar',
  imports: [NgClass, RouterOutlet, MatIcon, MatToolbar, MatSidenav, AsyncPipe, RouterLink, MatNavList, MatListItem, MatToolbarRow, MatSidenavContainer, MatSidenavContent],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {

  isLoggedIn$!: Observable<boolean>;
  // isMobile = false;
  isMobile$: Observable<boolean>;
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  isMobile = true;
  isCollapsed = true;

  constructor(private authService: AuthService, private router: Router, private breakpointObserver: BreakpointObserver) {

    this.isMobile$ = this.breakpointObserver
      .observe([Breakpoints.Handset])
      .pipe(
        map(result => result.matches),
        shareReplay() // partage le dernier résultat entre tous les abonnés
      );


    this.breakpointObserver.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      if (screenSize.matches) {
        this.isMobile = true;
      } else {
        this.isMobile = false;
      }
    });
  }

  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn$();


  }



  logout() {
    this.authService.logout();
    this.router.navigate(['/landing']);
  }

  get isOnLanding(): boolean {
    return this.router.url === '/landing';
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
}
