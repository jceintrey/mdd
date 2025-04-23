import { AsyncPipe, CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
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
import { Observable, tap } from 'rxjs';

@Component({
  selector: 'app-createpost',
  imports: [CommonModule, AsyncPipe, RouterModule, ReactiveFormsModule, MatIconModule, MatFormFieldModule, MatSelectModule, MatInputModule],
  templateUrl: './createpost.component.html',
  styleUrl: './createpost.component.scss'
})
export class CreatepostComponent implements OnInit {
  createPostForm!: FormGroup;
  topics$: Observable<Topic[]> = this.topicService.all();

  constructor(private formBuilder: FormBuilder, private router: Router, private topicService: TopicService, private postService: PostService, private snackBar: MatSnackBar) {

  }
  ngOnInit(): void {
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
  onSubmit() {
    console.log("submit create post");
    if (this.createPostForm.invalid) {
      this.createPostForm.markAllAsTouched();
      return;
    }

    const postRequest: PostRequest = this.createPostForm.value as PostRequest;
    console.log(postRequest);
    this.postService.createPost(postRequest).subscribe({
      next: () => {
        console.log("createPost ok");
        this.snackBar.open('Article créé avec succès', 'Fermer', { duration: 5000 });
        this.router.navigate(['/posts']);
      },
      error: () => {
        console.log("createPost nok");
        this.snackBar.open('Erreur lors de la création de l\'article', 'Fermer', { duration: 5000 });
      }

    }
    );

  }


}
