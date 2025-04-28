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


/**
 * The component displays a page with all posts related to subscribed topics.
 *
 *
 * @remarks
 * - Uses `SubscriptionService` to retrieve the user subscriptions
 * - Uses `PostService` to retrieve posts
 * - Build a filtered Observable from the two above Observable that contains the posts related to subscribed topics
 *
 * @see
 *  - Even if the user is the author of a topic, the topic is not showing if the user has not subscribed to the topic
 */
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
   * - get user subscriptions
   * - get all posts
   * - build the new Observable
   */
  ngOnInit(): void {
    this.subscriptions$ = this.subscriptionService.all()
      .pipe(takeUntil(this.destroy$));

    this.posts$ = this.postService.all().pipe(
      takeUntil(this.destroy$),
      map(res => res.content)
    );
    this.updateFilteredPosts();
  }


  /**
   * Join values emited from the two Observable on the topic id and return a new Observable sorted by date asc if sortUp and dsc else
   */
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

  /**
   * flip the sortUp flag and rebuild the filtered post Observable
   */
  flipSort() {
    this.isSortUp = !this.isSortUp;
    this.updateFilteredPosts();
  }


}

