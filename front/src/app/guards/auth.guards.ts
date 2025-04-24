import { Injectable } from "@angular/core";
import { AuthService } from "../core/services/auth.service";
import { CanActivate, Router, UrlTree } from "@angular/router";
import { map, Observable } from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class AuthGuard implements CanActivate {
    constructor(private authService: AuthService, private router: Router) { }

    /**
      * Determines whether a route can be activated.
      *
      * Subscribes to `authService.isLoggedIn$()`, and:
      * - If the user is authenticated (`true`), allows navigation.
      * - If the user is not authenticated (`false`), returns a UrlTree that redirects to `/greeting`.
      *
      * @returns An Observable emitting either:
      *  - `true` to allow navigation, or
      *  - a `UrlTree` to redirect the user to the greeting page.
      */
    canActivate(): Observable<boolean | UrlTree> {
        return this.authService.isLoggedIn$().pipe(
            map(isLogged => {
                return isLogged ? true : this.router.createUrlTree(['/greeting']);
            })
        );
    }
}