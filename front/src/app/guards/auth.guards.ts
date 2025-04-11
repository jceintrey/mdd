import { Injectable } from "@angular/core";
import { AuthService } from "../core/services/auth.service";
import { CanActivate, Router } from "@angular/router";
import { map, Observable } from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class AuthGuard implements CanActivate {
    constructor(private authService: AuthService, private router: Router) { }

    canActivate(): Observable<boolean> {
        return this.authService.isLogged$.pipe(
            map(isLogged => {
                console.log('AuthGuard - User logged in:', isLogged);
                if (!isLogged) {
                    this.router.navigate(['/landing']);
                }
                return isLogged;
            })
        );
    }
}