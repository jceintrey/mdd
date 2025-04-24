import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { PostService } from 'app/core/services/post.service';
import { Observable, Subject, takeUntil } from 'rxjs';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Post } from 'app/core/interfaces/post.interface';
import { AsyncPipe, DatePipe } from '@angular/common';
import { MatListModule } from '@angular/material/list';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommentRequest } from 'app/core/interfaces/commentRequest.interface';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

/**
 * Displays a detailed display of a post with a its comments and a comment form
 *
 * @remarks
 * - Uses `ActivatedRoute` to get the active route param containing the post id
 * - Uses `PostService` to get the expected post
 * - Builds a reactive `FormGroup` with validation constraints for new comments
 */
@Component({
  selector: 'app-postdetails',
  imports: [MatIconModule, MatButtonModule, MatCardModule, MatDividerModule, MatListModule, DatePipe, AsyncPipe, RouterLink, MatFormFieldModule, MatInputModule, ReactiveFormsModule],
  templateUrl: './postdetails.component.html',
  styleUrl: './postdetails.component.scss'
})
export class PostDetailsComponent implements OnInit, OnDestroy {
  post$!: Observable<Post>;
  commentForm!: FormGroup;
  postId!: string | null;
  private destroy$ = new Subject<void>();

  constructor(private postService: PostService, private route: ActivatedRoute, private fb: FormBuilder, private snackBar: MatSnackBar) { }

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
    * - get the post id from active route
    * - get the post by its id
    * - build the comment form
    */
  ngOnInit(): void {
    this.postId = this.route.snapshot.paramMap.get('id');

    if (this.postId) {
      this.post$ = this.postService.getPost(this.postId)
        .pipe(takeUntil(this.destroy$));
      this.commentForm = this.fb.group({
        content: ['', [Validators.required, Validators.minLength(3)]]
      });
    }
  }

  /**
  * Submit the form by calling postService.addComment
  */
  onSubmit() {
    if (this.postId) {
      const commentRequest: CommentRequest = this.commentForm.value as CommentRequest;
      this.postService.addComment(this.postId, commentRequest)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (value) => {
            this.post$ = this.postService.getPost(this.postId)
              .pipe(takeUntil(this.destroy$));

            this.commentForm.reset();
          },
          error: () => {
            this.snackBar.open('Erreur lors de la création de l\'article', 'Fermer', { duration: 5000 });

          },
        });
    }


  }
}
