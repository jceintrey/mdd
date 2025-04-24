import { HttpEvent, HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "../core/services/auth.service";


/**
 * HTTP interceptor function that attaches a Bearer token to outgoing API requests.
 *
 * @remarks
 * - Skips token attachment for login and registration endpoints (`/api/auth/login` and `/api/auth/register`).
 * - Uses Angular’s `inject()` to retrieve `AuthService` in a standalone interceptor.
 * - Clones the request and sets the `Authorization` header when a token is present.
 *
 * @param req - The outgoing `HttpRequest`.
 * @param next - The next `HttpHandlerFn` in the chain.
 * @returns An `Observable` of the `HttpEvent` stream, forwarding either the original or cloned request.
 *
 */
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