import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { User } from '../../../../core/interfaces/User.interface';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../../core/services/auth.service';
import { UserService } from '../../../../core/services/user.service';
import { TopicService } from 'app/core/services/topic.service';
import { Observable } from 'rxjs';
import { Topic } from 'app/core/interfaces/topic.interface';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-me',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatDividerModule],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent {
  user: User | null = null;
  errorMessage: string | null = null;
  topics$!: Observable<Topic[]>;
  userForm!: FormGroup;
  constructor(private authService: AuthService, private userService: UserService, private topicService: TopicService, private fb: FormBuilder) { }

  ngOnInit(): void {

    this.userService.getMe().subscribe({
      next: (data) => {
        this.user = data

        this.userForm = this.fb.group({
          username: ['', [Validators.minLength(3)]],
          email: ['', [Validators.email]],
          password: ['', [Validators.minLength(3)]]
        });

        this.topics$ = this.topicService.all()

      },
      error: (err) => {
        console.log("Error on Mecomponent.getMe()");
        this.errorMessage = "Error while getting infos";
      }
    })
  }


  onSubmit() {

    console.log("try to submit userForm");
  }
}
