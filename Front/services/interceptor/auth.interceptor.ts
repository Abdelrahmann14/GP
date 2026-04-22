import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../security/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = sessionStorage.getItem('token');
    const lang = localStorage.getItem('lang') || 'en'; // لو اللغة مش متحددة، هنفترض "en"

    const headers: any = {
      'Accept-Language': lang
    };

    if (this.authService.isUserLogin() && token) {
      headers.Authorization = 'Bearer ' + token;
    }

    request = request.clone({
      setHeaders: headers
    });

    return next.handle(request);
  }

}
