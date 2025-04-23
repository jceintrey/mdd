import { Component } from '@angular/core';
import { User, UserUpdateRequest } from '../../../../core/interfaces/User.interface';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../../core/services/auth.service';
import { UserService } from '../../../../core/services/user.service';
import { TopicService } from 'app/core/services/topic.service';
import { map, Observable } from 'rxjs';
import { Topic } from 'app/core/interfaces/topic.interface';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';
import { TopicComponent } from "../../../topics/topic/topic.component";
import { SubscriptionService } from 'app/core/services/subscription.service';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-me',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatDividerModule, TopicComponent, MatIconModule],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent {

  user: User | null = null;
  errorMessage: string | null = null;
  topics$!: Observable<Topic[]>;
  userForm!: FormGroup;
  hidePassword = true;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private topicService: TopicService,
    private fb: FormBuilder,
    private subscriptionService: SubscriptionService,
    private snack: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.userService.getMe().subscribe({
      next: (data) => {
        this.userForm = this.fb.group({
          username: [data.username, [Validators.required, Validators.minLength(3)]],
          email: [data.email, [Validators.required, Validators.email]],
          password: ['']
        });
        this.reloadSubscriptions();
      },
      error: (err) => {
        console.log("Error on Mecomponent.getMe()");
        this.errorMessage = "Error while getting infos";
      }
    });
  }


  onSubmit() {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      return;
    }
    let userUpdate: UserUpdateRequest = this.userForm.value as UserUpdateRequest;
    if (!userUpdate.password) {
      delete userUpdate.password;
    }
    this.userService.update(userUpdate).subscribe({
      next: () => {
        this.snack.open("Profil utilisateur mis à jour avec succès", "Fermer", { duration: 5000 });
      },
      error: (err: any) => {
        this.snack.open("Erreur lors de la mise à jour du profil", "Fermer", { duration: 5000 });
      }
    })
  }

  reloadSubscriptions() {
    this.topics$ = this.topicService.all().pipe(
      map(topics => topics.filter(topic => topic.subscribed))
    );
  }

  unsubscribe(topicId: number) {
    this.subscriptionService.unsubscribe(topicId).subscribe({
      next: () => {
        this.reloadSubscriptions();
      },
      error: (err: any) => {
        this.snack.open("Erreur lors de la suppression de l'abonnement", "Fermer", { duration: 5000 });
      }
    });
  }
}
