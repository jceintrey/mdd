import { Component, OnDestroy, OnInit } from '@angular/core';
import { User, UserUpdateRequest } from '../../../../core/interfaces/User.interface';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../../core/services/auth.service';
import { UserService } from '../../../../core/services/user.service';
import { TopicService } from 'app/core/services/topic.service';
import { map, Observable, Subject, takeUntil } from 'rxjs';
import { Topic } from 'app/core/interfaces/topic.interface';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';
import { TopicComponent } from "../../../topics/topic/topic.component";
import { SubscriptionService } from 'app/core/services/subscription.service';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';


/**
 * The component displays a page showing
 * - the user informations like thier username, email with the possibility to change them plus password
 * - their subscriptions with the possibility to unsusbscribe from
 *
 * @remarks
 * - Uses `UserService` to retrieve or update the user profile
 * - Uses `SubscriptionService` to unsubscribe from a topic
 *
 */
@Component({
  selector: 'app-me',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatDividerModule, TopicComponent, MatIconModule],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent implements OnInit, OnDestroy {

  user: User | null = null;
  errorMessage: string | null = null;
  topics$!: Observable<Topic[]>;
  userForm!: FormGroup;
  private destroy$ = new Subject<void>();

  constructor(
    private userService: UserService,
    private topicService: TopicService,
    private fb: FormBuilder,
    private subscriptionService: SubscriptionService,
    private snack: MatSnackBar
  ) { }

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
  * - get the topics
  */
  ngOnInit(): void {
    this.userService.getMe()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
          this.userForm = this.fb.group({
            username: [data.username, [Validators.required, Validators.minLength(3)]],
            email: [data.email, [Validators.required, Validators.email]],
            password: ['']
          });
          this.reloadSubscriptions();
        },
        error: (err) => {
          this.errorMessage = "Error while getting infos";
        }
      });
  }

  /**
   * Used to update the user profile like their email, username or password.
   * Submit the form by calling userService.update
   */
  onSubmit() {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      return;
    }
    let userUpdate: UserUpdateRequest = this.userForm.value as UserUpdateRequest;
    if (!userUpdate.password) {
      delete userUpdate.password;
    }
    this.userService.update(userUpdate)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.snack.open("Profil utilisateur mis à jour avec succès", "Fermer", { duration: 5000 });
        },
        error: (err: any) => {
          this.snack.open("Erreur lors de la mise à jour du profil", "Fermer", { duration: 5000 });
        }
      })
  }

  /**
    * Used to reload the subscription list after any change on subscriptions
    */
  private reloadSubscriptions() {
    this.topics$ = this.topicService.all().pipe(
      takeUntil(this.destroy$),
      map(topics => topics.filter(topic => topic.subscribed))
    );
  }

  /**
* Unsubscribe from the topic by its id and reload subscriptions then
*/
  unsubscribe(topicId: number) {
    this.subscriptionService.unsubscribe(topicId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.reloadSubscriptions();
        },
        error: (err: any) => {
          this.snack.open("Erreur lors de la suppression de l'abonnement", "Fermer", { duration: 5000 });
        }
      });
  }
}
