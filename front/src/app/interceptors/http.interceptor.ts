import { HttpEvent, HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "../feature/auth/services/auth.service";



export function loggingInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  console.log(req.url);
  console.log(req.headers);
  console.log(req.method);
  return next(req);
}


export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const token = inject(AuthService).getToken() ?? '';
  const cloned = req.clone({ headers: req.headers.set('Authorization', `Bearer ${token}`) });
  return next(cloned);
}




