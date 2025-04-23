import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScreenService {

  /** Emits true when viewport ≤ 800px, false otherwise */
  public isMobile$ = new BehaviorSubject<boolean>(false);

}
