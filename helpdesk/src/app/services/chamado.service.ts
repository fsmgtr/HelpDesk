import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../configuracoes/api.config';
import { Chamado } from '../models/chamado';
import { Cliente } from '../models/cliente';

@Injectable({
  providedIn: 'root'
})
export class ChamadoService {

  constructor(private http: HttpClient) { }



delete(id: any): Observable<Cliente>{
  return this.http.delete<Cliente>(`${API_CONFIG.baseUrl}/clientes/${id}`);
}

consultarPorId(id: any): Observable<Cliente> {
  return this.http.get<Cliente>(`${API_CONFIG.baseUrl}/clientes/${id}`);
}

update(cliente: Cliente): Observable<Cliente> {
  return this.http.put<Cliente>(`${API_CONFIG.baseUrl}/clientes/${cliente.id}`, cliente);
}

buscarTodos(): Observable<Chamado[]>{
 return this.http.get<Chamado[]>(`${API_CONFIG.baseUrl}/chamados`);
}

create(cliente : Cliente): Observable<Cliente>{
  return this.http.post<Cliente>(`${API_CONFIG.baseUrl}/clientes`, cliente);
}

 isValidCPF(cpf: any) {
  if (typeof cpf !== "string") return false
  cpf = cpf.replace(/[\s.-]*/igm, '')
  if (
      !cpf ||
      cpf.length != 11 ||
      cpf == "00000000000" ||
      cpf == "11111111111" ||
      cpf == "22222222222" ||
      cpf == "33333333333" ||
      cpf == "44444444444" ||
      cpf == "55555555555" ||
      cpf == "66666666666" ||
      cpf == "77777777777" ||
      cpf == "88888888888" ||
      cpf == "99999999999" 
  ) {
      return false
  }
  var soma = 0
  var resto
  for (var i = 1; i <= 9; i++) 
      soma = soma + parseInt(cpf.substring(i-1, i)) * (11 - i)
  resto = (soma * 10) % 11
  if ((resto == 10) || (resto == 11))  resto = 0
  if (resto != parseInt(cpf.substring(9, 10)) ) return false
  soma = 0
  for (var i = 1; i <= 10; i++) 
      soma = soma + parseInt(cpf.substring(i-1, i)) * (12 - i)
  resto = (soma * 10) % 11
  if ((resto == 10) || (resto == 11))  resto = 0
  if (resto != parseInt(cpf.substring(10, 11) ) ) return false
  return true
}


}
