import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Credenciais } from 'src/app/models/credenciais';
import { AutenticacaoService } from 'src/app/services/autenticacao.service';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private toast: ToastrService,
    private serviceAutenticacao: AutenticacaoService, private router: Router) { }
  creds: Credenciais = {
    email: "",
    senha: ""
  }

  email = new FormControl(null, Validators.email);
  senha = new FormControl(null, Validators.minLength(2));
  

  logar() {
   this.serviceAutenticacao.autenticacao(this.creds).subscribe(resposta =>{
    this.serviceAutenticacao.sucessoNoLogin(resposta.headers.get('Authorization').substring(7))
    this.router.navigate(['']);   
    
     
   }, ()=> {
     this.toast.error('Usuário e/ou senha inválidos');
     console.log(this.creds.email);
   })
  }
  ngOnInit(): void {
  }

  validaCampos(): boolean {
    return this.email.valid && this.senha.valid;
  }



}
