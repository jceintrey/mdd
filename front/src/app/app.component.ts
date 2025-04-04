import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './feature/auth/services/auth.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { AsyncPipe } from '@angular/common';

const materialModule = [
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatSnackBarModule,
  MatToolbarModule,
];


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, AsyncPipe, ...materialModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor(
    private authService: AuthService,
    private router: Router,) {
  }

  public $isLogged(): Observable<boolean> {
    return this.authService.$isLogged();
  }

  logout() {
    throw new Error('Method not implemented.');
  }
  title = 'front';
}
