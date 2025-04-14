import { Component, Input } from '@angular/core';
import { map, Observable, of } from 'rxjs';
import { Post } from '../../../core/interfaces/post.interface';
import { PostService } from '../../../core/services/post.service';
import { AsyncPipe, DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink, RouterModule } from '@angular/router';
import { MatIcon } from '@angular/material/icon';
import { BreakpointObserver } from '@angular/cdk/layout';

@Component({
  selector: 'app-post',
  imports: [AsyncPipe, RouterLink, RouterModule, MatCardModule, MatButtonModule, DatePipe, RouterLink, MatIcon],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss'
})
export class PostComponent {

  isMobile = true;
  isSortUp = true;

  posts$ = this.postService.all().pipe(
    map(res => res.content)
  );



  constructor(
    private observer: BreakpointObserver,
    private postService: PostService
  ) {
    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      if (screenSize.matches) {
        this.isMobile = true;
      } else {
        this.isMobile = false;
      }
    });
  }


  flipSort() {
    this.isSortUp = !this.isSortUp;
    if (this.isSortUp) {
      this.posts$ = this.postService.all().pipe(
        map(res => res.content.sort((a, b) => {
          const dateA = new Date(a.created_at).getTime();
          const dateB = new Date(b.created_at).getTime();
          return this.isSortUp ? dateB - dateA : dateA - dateB;
        })));
    }

  }
}
