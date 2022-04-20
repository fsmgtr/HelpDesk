import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs/internal/Observable';
import { API_CONFIG } from '../configuracoes/api.config';
import { Credenciais } from '../models/credenciais';

@Injectable({
  providedIn: 'root'
})
export class AutenticacaoService {

  jwtService: JwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient) { }

  autenticacao(credenciais: Credenciais) {
    return this.http.post(`${API_CONFIG.baseUrl}/login`, credenciais, {
      observe: 'response',
      responseType: 'text'
    })
  }

  sucessoNoLogin(tokenDeAutorizacao: string) {
    localStorage.setItem('token', tokenDeAutorizacao);
  }

  estaAutenticado() {
    let token = localStorage.getItem('token')
    if (token != null) {
      return !this.jwtService.isTokenExpired(token)
    }
    return false;
  }

logout(){
  localStorage.clear();
}

}
