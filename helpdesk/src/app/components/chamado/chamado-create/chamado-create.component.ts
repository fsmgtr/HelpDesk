import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Chamado } from 'src/app/models/chamado';
import { Cliente } from 'src/app/models/cliente';
import { Tecnico } from 'src/app/models/tecnico';
import { ChamadoService } from 'src/app/services/chamado.service';
import { ClienteService } from 'src/app/services/cliente.service';
import { TecnicoService } from 'src/app/services/tecnico.service';

@Component({
  selector: 'app-chamado-create',
  templateUrl: './chamado-create.component.html',
  styleUrls: ['./chamado-create.component.css']
})
export class ChamadoCreateComponent implements OnInit {

  chamado: Chamado = {
    prioridade: '',
    status: '',
    titulo: '',
    observacao: '',
    tecnico: '',
    cliente: '',
    nomeTecnico: '',
    nomeCliente: '',
  }
  clientes: Cliente[] = [];
  tecnicos: Tecnico[] = [];

  prioridade: FormControl = new FormControl(null, [Validators.required]);
  status: FormControl = new FormControl(null, [Validators.required]);
  titulo: FormControl = new FormControl(null, [Validators.required]);
  observacao: FormControl = new FormControl(null, [Validators.required]);
  tecnico: FormControl = new FormControl(null, [Validators.required]);
  cliente: FormControl = new FormControl(null, [Validators.required]);
  constructor(
    private clienteService: ClienteService,
    private tecnicoService: TecnicoService,
    private chamadoService: ChamadoService,
    private toastService: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.listarTodosClientes();
    this.listarTodosTecnicos();
  }

  create(): void {
    this.chamadoService.criarChamado(this.chamado).subscribe(resposta => {
      this.toastService.success("Chamado Criado com Sucesso!", "Novo Chamado.");
      this.router.navigate(['chamados']);
    }, ex => this.toastService.error(ex.error.error));
  }

  listarTodosClientes(): void {
    this.clienteService.buscarTodos().subscribe(resposta => {
      this.clientes = resposta;
    })
  }

  listarTodosTecnicos(): void {
    this.tecnicoService.buscarTodos().subscribe(resposta => {
      this.tecnicos = resposta;
    })
  }



  validaCampos(): boolean {
    return this.prioridade.valid &&
      this.status.valid &&
      this.titulo.valid &&
      this.observacao.valid &&
      this.tecnico.valid &&
      this.cliente.valid
  }

}
