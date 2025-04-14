import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { PostService } from 'app/core/services/post.service';
import { map, Observable, of } from 'rxjs';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'app/core/interfaces/post.interface';
import { AsyncPipe, CommonModule } from '@angular/common';
import { MatListModule } from '@angular/material/list';


@Component({
  selector: 'app-postdetails',
  imports: [MatIconModule, MatCardModule, MatDividerModule, MatListModule, AsyncPipe],
  templateUrl: './postdetails.component.html',
  styleUrl: './postdetails.component.scss'
})
export class PostDetailsComponent implements OnInit {
  post$!: Observable<Post>;

  constructor(private postService: PostService, private route: ActivatedRoute) { }
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    console.log('ID article:', id);
    if (id) {
      this.post$ = this.postService.getPost(id);
    }
  }


}
