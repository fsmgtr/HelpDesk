import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-delete',
  templateUrl: './cliente-delete.component.html',
  styleUrls: ['./cliente-delete.component.css']
})
export class ClienteDeleteComponent implements OnInit {


  cliente: Cliente = {
    id: '',
    nome: '',
    cpf: '',
    email: '',
    senha: '',
    perfis: [],
    dataCriacao: ''
  }
 
  constructor(private clienteService: ClienteService,
    private toast: ToastrService,
    private router: Router,
    private activateRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.cliente.id = this.activateRoute.snapshot.paramMap.get('id');
    this.buscarPorId();
  }

 

  buscarPorId(): void {
    this.clienteService.consultarPorId(this.cliente.id).subscribe(resposta => {
      resposta.perfis = [];
      this.cliente = resposta;
    })
  }

  delete(): void {
    this.clienteService.delete(this.cliente.id).subscribe(() => {
      this.toast.success("cliente Deletado com Sucesso");
      this.router.navigate(['clientes']);
    }, ex => {
      console.log(ex);
      if (ex.error.errors) {
        ex.error.errors.forEach(element => {
          this.toast.error(element.message);
        });
      } else {
        this.toast.error(ex.error.message);
        console.log(ex.error.message);
      }
    }
    )
    
  }
  

}
