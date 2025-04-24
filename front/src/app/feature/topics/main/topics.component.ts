import { Component, OnDestroy, OnInit } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { TopicService } from 'app/core/services/topic.service';
import { SubscriptionService } from 'app/core/services/subscription.service';
import { TopicComponent } from "../topic/topic.component";
import { Observable, Subject, takeUntil } from 'rxjs';
import { Topic } from 'app/core/interfaces/topic.interface';

/**
 * The component displays a page showing all topics
 *
 * @remarks
 * - Uses `TopicService` to retrieve the topics with a subscribed flag to know if user as subscribed or not
 * - Uses `SubscriptionService` to subscribe or unsubscribe from a topic
 *
 */
@Component({
  selector: 'app-post',
  imports: [AsyncPipe, MatCardModule, MatButtonModule, TopicComponent],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss'
})
export class TopicsComponent implements OnInit, OnDestroy {

  topics$!: Observable<Topic[]>;

  private destroy$ = new Subject<void>();

  constructor(
    private topicService: TopicService,
    private subscriptionService: SubscriptionService,
  ) { }


  /**
    * Initializes the component.
    *
    * - get the topics
    */
  ngOnInit(): void {
    this.topics$ = this.topicService.all()
      .pipe(takeUntil(this.destroy$));
  }

  /**
  * Cleans up any active subscriptions to prevent memory leaks.
  */
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  /**
  * Subscribe to the topic by its id and reload topics then
  */
  protected subscribe(id: number): void {
    this.subscriptionService.subscribe(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.reloadTopics();
        },
        error: (err: any) => {
          console.log("topic.subscribe error");
        }
      });
  }

  /**
  * Unsubscribe from the topic by its id and reload topics then
  */
  protected unsubscribe(id: number): void {
    this.subscriptionService.unsubscribe(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.reloadTopics();
        },
        error: (err: any) => {
          console.log("topic.unsubscribe error");
        }
      });
  }

  /**
   * Used to reload the topic list after any chnage on topics
   */
  private reloadTopics(): void {
    this.topics$ = this.topicService.all()
      .pipe(takeUntil(this.destroy$));

  }
}
