import { NgClass, NgStyle } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { Topic } from 'app/core/interfaces/topic.interface';

/**
 * The component displays a topic card
 *
 * @remarks
 * - @Input topic to enable the topic value injection from TopicsComponent
 * - @Output subscribe and unsubscribe to send back a subscribe or unsubscribe event to the parent component
 *
 */
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


  /**
   * onToggleSubscription emit the topic id in the corresponding property.
   * The business is done into the Topics parent component
   */
  onToggleSubscription() {
    if (this.topic.subscribed) {
      this.unsubscribe.emit(this.topic.id);
    } else {
      this.subscribe.emit(this.topic.id);
    }
  }

}
