import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AutenticacaoService } from 'src/app/services/autenticacao.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  constructor(private router: Router, private  autenticacao: AutenticacaoService, private toast: ToastrService) { }

  ngOnInit(): void {
    this.router.navigate(['home'])
  }

  logout(){
    this.router.navigate(['login']);
    this.autenticacao.logout();
    this.toast.info('Logout realizado com Sucesso!' , 'Logout', {timeOut: 10000});
  }

}
