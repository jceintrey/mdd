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

  constructor(private postService: PostService, private route: ActivatedRoute, private fb: FormBuilder,) { }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void {
    this.postId = this.route.snapshot.paramMap.get('id');
    console.log('ID article:', this.postId);
    if (this.postId) {
      this.post$ = this.postService.getPost(this.postId)
        .pipe(takeUntil(this.destroy$));
      this.commentForm = this.fb.group({
        content: ['', [Validators.required, Validators.minLength(3)]]
      });
    }
  }

  onSubmit() {
    if (this.postId) {
      const commentRequest: CommentRequest = this.commentForm.value as CommentRequest;
      this.postService.addComment(this.postId, commentRequest)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (value) => {
            console.log(value);
            this.post$ = this.postService.getPost(this.postId)
              .pipe(takeUntil(this.destroy$));

            this.commentForm.reset();
          },
          error: (err) => {
            console.log("Error during post comment");
            console.log(err);
          },
        });
    }


  }
}
