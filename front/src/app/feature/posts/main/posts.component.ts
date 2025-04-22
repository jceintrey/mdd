import { Component, Input, OnInit } from '@angular/core';
import { combineLatest, filter, map, Observable, of } from 'rxjs';
import { Post } from '../../../core/interfaces/post.interface';
import { PostService } from '../../../core/services/post.service';
import { AsyncPipe, DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink, RouterModule } from '@angular/router';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { BreakpointObserver } from '@angular/cdk/layout';
import { PostComponent } from '../post/post.component';
import { SubscriptionService } from 'app/core/services/subscription.service';
import { Subscription } from 'app/core/interfaces/subscription.interface';

@Component({
  selector: 'app-posts',
  imports: [AsyncPipe, RouterLink, RouterModule, MatCardModule, MatButtonModule, RouterLink, MatIconModule, PostComponent],
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.scss'
})
export class PostsComponent implements OnInit {


  isSortUp = true;

  posts$!: Observable<Post[]>
  filteredPosts$!: Observable<Post[]>
  subscriptions$!: Observable<Subscription[]>;



  constructor(
    private postService: PostService,
    private subscriptionService: SubscriptionService
  ) {

  }
  ngOnInit(): void {
    this.subscriptions$ = this.subscriptionService.all();

    this.posts$ = this.postService.all().pipe(
      map(res => res.content)
    );

    this.updateFilteredPosts();
  }



  private updateFilteredPosts() {
    this.filteredPosts$ = combineLatest([
      this.posts$,
      this.subscriptions$
    ]).pipe(
      map(([posts, subscriptions]) => {
        const subscribedPost = new Set(subscriptions.map(s => s.topic_id));
        const filtered = posts.filter(p => subscribedPost.has(p.topic.id));
        return filtered.sort((a, b) => {
          const dateA = new Date(a.created_at).getTime();
          const dateB = new Date(b.created_at).getTime();
          return this.isSortUp ? dateB - dateA : dateA - dateB;
        });
      })
    );
  }
  flipSort() {
    this.isSortUp = !this.isSortUp;
    this.updateFilteredPosts();
  }


}

