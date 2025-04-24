import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

/**
 * Service used to centralize the screen configuration.
 * Used by any component that need to know the screen configuration i.e mobile or not mobile.
 */
@Injectable({
  providedIn: 'root'
})
export class ScreenService {

  /** Emits true when viewport ≤ 800px, false otherwise */
  public isMobile$ = new BehaviorSubject<boolean>(false);

}
