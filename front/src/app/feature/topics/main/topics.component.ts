import { Component } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { TopicService } from 'app/core/services/topic.service';
import { SubscriptionService } from 'app/core/services/subscription.service';
import { TopicComponent } from "../topic/topic.component";

@Component({
  selector: 'app-post',
  imports: [AsyncPipe, MatCardModule, MatButtonModule, TopicComponent],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss'
})
export class TopicsComponent {

  topics$ = this.topicService.all();

  constructor(
    private topicService: TopicService,
    private subscriptionService: SubscriptionService,
  ) { }

  protected subscribe(id: number): void {
    this.subscriptionService.subscribe(id).subscribe({
      next: () => {
        console.log("topic.subscribe ok");
        this.reloadTopics();
      },
      error: (err: any) => {
        console.log("topic.subscribe error");

      }
    });
  }

  protected unsubscribe(id: number): void {
    this.subscriptionService.unsubscribe(id).subscribe({
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
    this.topics$ = this.topicService.all();

  }
}
