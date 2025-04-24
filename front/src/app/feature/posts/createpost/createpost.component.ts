import { AsyncPipe, CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select'
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, RouterModule } from '@angular/router';
import { PostRequest } from 'app/core/interfaces/postRequest.interface';
import { Topic } from 'app/core/interfaces/topic.interface';
import { PostService } from 'app/core/services/post.service';
import { TopicService } from 'app/core/services/topic.service';
import { Observable, Subject, takeUntil, tap } from 'rxjs';

/**
 * Displays a page that enable the user to add a new Post.
 *
 * @remarks
 * - Uses `TopicService` to retrieve and show the Topic list
 * - Uses `PostService` to submit the new post request
 * - Builds a reactive `FormGroup` with validation constraints for the new post
 * - Uses `MatSnackBar` to popup the user about the creation result
 */
@Component({
  selector: 'app-createpost',
  imports: [CommonModule, AsyncPipe, RouterModule, ReactiveFormsModule, MatIconModule, MatFormFieldModule, MatSelectModule, MatInputModule],
  templateUrl: './createpost.component.html',
  styleUrl: './createpost.component.scss'
})
export class CreatepostComponent implements OnInit, OnDestroy {
  createPostForm!: FormGroup;
  topics$: Observable<Topic[]> = this.topicService.all();
  private destroy$ = new Subject<void>();

  constructor(private formBuilder: FormBuilder, private router: Router, private topicService: TopicService, private postService: PostService, private snackBar: MatSnackBar) {

  }

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
  * - Build `createPostForm`
  */
  ngOnInit(): void {
    this.buildForm();
  }

  /**
  * Enable the user to create a new post
  * Submit the form by calling postService.createPost
  */
  onSubmit() {
    if (this.createPostForm.invalid) {
      this.createPostForm.markAllAsTouched();
      return;
    }

    const postRequest: PostRequest = this.createPostForm.value as PostRequest;
    this.postService.createPost(postRequest)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.snackBar.open('Article créé avec succès', 'Fermer', { duration: 5000 });
          this.router.navigate(['/posts']);
        },
        error: () => {
          this.snackBar.open('Erreur lors de la création de l\'article', 'Fermer', { duration: 5000 });
        }
      }
      );
  }

  /**
  * Build the form with its constraints
  */
  private buildForm() {
    this.createPostForm = this.formBuilder.group({
      topic_id: [
        '',
        [
          Validators.required,
        ]
      ],
      title: [
        '',
        [
          Validators.required,
        ]
      ],
      content: [
        '',
        [
          Validators.required,
          Validators.min(10),
          Validators.max(5000)
        ]
      ]
    });
  }


}
