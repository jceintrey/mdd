import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { User } from '../../../../core/interfaces/User';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../../core/services/auth.service';
import { UserService } from '../../../../core/services/user.service';

@Component({
  selector: 'app-me',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent {
  user: User | null = null;
  errorMessage: string | null = null;


  constructor(private http: HttpClient, private authService: AuthService, private userService: UserService) { }

  ngOnInit(): void {

    this.userService.getMe().subscribe({
      next: (data) => (this.user = data),
      error: (err) => {
        console.log("Error on Mecomponent.getMe()");
        this.errorMessage = "Error while getting infos";
      }
    })
  }
  logout() {
    this.authService.logout();
    console.log("me.logout")
  }
}
