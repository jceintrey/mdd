import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { BehaviorSubject, combineLatest, filter, map, Observable, of, Subject, takeUntil } from 'rxjs';
import { Post } from '../../../core/interfaces/post.interface';
import { PostService } from '../../../core/services/post.service';
import { AsyncPipe, DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { PostComponent } from '../post/post.component';
import { SubscriptionService } from 'app/core/services/subscription.service';
import { Subscription } from 'app/core/interfaces/subscription.interface';

@Component({
  selector: 'app-posts',
  imports: [AsyncPipe, RouterLink, RouterModule, MatCardModule, MatButtonModule, RouterLink, MatIconModule, PostComponent],
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.scss'
})
export class PostsComponent implements OnInit, OnDestroy {

  isSortUp = true;
  posts$!: Observable<Post[]>
  filteredPosts$!: Observable<Post[]>
  subscriptions$!: Observable<Subscription[]>;
  private destroy$ = new Subject<void>;


  constructor(
    private postService: PostService,
    private subscriptionService: SubscriptionService
  ) { }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit(): void {
    this.subscriptions$ = this.subscriptionService.all()
      .pipe(takeUntil(this.destroy$));

    this.posts$ = this.postService.all().pipe(
      takeUntil(this.destroy$),
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

