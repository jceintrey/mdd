import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { User } from '../../interfaces/User';
import { CommonModule } from '@angular/common';

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


  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<User>('/api/user/me').subscribe({
      next: (data) => (this.user = data),
      error: (err) => {
        console.error('Erreur de chargement du profil', err);
        this.errorMessage = 'Impossible de récupérer les infos utilisateur.';
      },
    });
  }

}
