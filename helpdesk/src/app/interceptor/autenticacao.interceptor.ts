import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AutenticacaoInterceptor implements HttpInterceptor {

  constructor() { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token = localStorage.getItem('token');
    if (token) {
      const cloqueDaRequisicao = request.clone({ headers: request.headers.set('Authorization', `Bearer ${token}`) })
      return next.handle(cloqueDaRequisicao);
    } else {
      return next.handle(request);
    }

    return next.handle(request);
  }
}

export const AutenticacaoInterceptorProvider = [{
  provide: HTTP_INTERCEPTORS,
  useClass: AutenticacaoInterceptor,
  multi: true
}

]