import { DatePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import { Post } from 'app/core/interfaces/post.interface';


/**
 * The component displays a a post card
 *
 * @remarks
 * - @Input to enable the Post value injection from PostsComponent
 *
 */
@Component({
  selector: 'app-post',
  imports: [MatCardModule, DatePipe, RouterLink],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss'
})
export class PostComponent {
  @Input() post!: Post;
}
