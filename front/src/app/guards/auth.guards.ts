import { Injectable } from "@angular/core";
import { AuthService } from "../core/services/auth.service";
import { CanActivate, Router, UrlTree } from "@angular/router";
import { map, Observable } from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class AuthGuard implements CanActivate {
    constructor(private authService: AuthService, private router: Router) { }

    // canActivate(): Observable<boolean> {
    //     return this.authService.isLogged$.pipe(
    //         map(isLogged => {
    //             if (!isLogged) {
    //                 this.router.navigate(['/greeting']);
    //             }
    //             return isLogged;
    //         })
    //     );
    // }


    canActivate(): Observable<boolean | UrlTree> {
        return this.authService.isLoggedIn$().pipe(
            map(isLogged => {
                return isLogged ? true : this.router.createUrlTree(['/greeting']);
            })
        );
    }
}