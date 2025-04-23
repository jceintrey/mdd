import { Component, OnDestroy, OnInit } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { TopicService } from 'app/core/services/topic.service';
import { SubscriptionService } from 'app/core/services/subscription.service';
import { TopicComponent } from "../topic/topic.component";
import { Observable, Subject, takeUntil } from 'rxjs';
import { Topic } from 'app/core/interfaces/topic.interface';

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


  ngOnInit(): void {
    this.topics$ = this.topicService.all()
      .pipe(takeUntil(this.destroy$));
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

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

  protected unsubscribe(id: number): void {
    this.subscriptionService.unsubscribe(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          console.log("topic.unsubscribe ok");
          this.reloadTopics();
        },
        error: (err: any) => {
          console.log("topic.unsubscribe error");

        }
      });
  }

  private reloadTopics(): void {
    this.topics$ = this.topicService.all()
      .pipe(takeUntil(this.destroy$));

  }
}
