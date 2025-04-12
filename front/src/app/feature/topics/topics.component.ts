import { Component, Input } from '@angular/core';
import { map, Observable, of } from 'rxjs';
import { AsyncPipe, DatePipe, NgClass } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import { MatIcon } from '@angular/material/icon';
import { BreakpointObserver } from '@angular/cdk/layout';
import { TopicService } from 'app/core/services/topic.service';
import { SubscriptionService } from 'app/core/services/subscription.service';

@Component({
  selector: 'app-post',
  imports: [AsyncPipe, MatCardModule, MatButtonModule, RouterLink, NgClass],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss'
})
export class TopicsComponent {


  isMobile = true;


  topics$ = this.topicService.all();



  constructor(
    private observer: BreakpointObserver,
    private topicService: TopicService,
    private subscriptionService: SubscriptionService,

  ) {
    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      if (screenSize.matches) {
        this.isMobile = true;
      } else {
        this.isMobile = false;
      }
    });
  }

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
