import { HttpEvent, HttpHandler, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "../core/services/auth.service";



export function loggingInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  console.log(req.url);
  console.log(req.headers);
  console.log(req.method);
  return next(req);
}

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {

  const authService = inject(AuthService);
  if (req.url.includes('/api/auth/login') || req.url.includes('/api/auth/register')) {
    return next(req);
  }
  const token = authService.getToken();

  if (token) {
    const clonedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next(clonedReq);
  }

  return next(req);
}