import { NgClass, NgStyle } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { Topic } from 'app/core/interfaces/topic.interface';

@Component({
  selector: 'app-topic',
  imports: [MatCardModule, NgClass],
  templateUrl: './topic.component.html',
  styleUrl: './topic.component.scss'
})
export class TopicComponent {
  @Input() topic!: Topic;

  @Output() subscribe = new EventEmitter<number>();
  @Output() unsubscribe = new EventEmitter<number>();


  onToggleSubscription() {
    if (this.topic.subscribed) {
      this.unsubscribe.emit(this.topic.id);
    } else {
      this.subscribe.emit(this.topic.id);
    }
  }

}
