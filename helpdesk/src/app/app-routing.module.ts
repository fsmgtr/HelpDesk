import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AutenticacaoGuard } from './autenticacao/autenticacao.guard';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { NavComponent } from './components/nav/nav.component';
import { TecnicoCreateComponent } from './components/tecnico/tecnico-create/tecnico-create.component';
import { TecnicoDeleteComponent } from './components/tecnico/tecnico-delete/tecnico-delete.component';
import { TecnicoListComponent } from './components/tecnico/tecnico-list/tecnico-list.component';
import { TecnicoUpdateComponent } from './components/tecnico/tecnico-update/tecnico-update.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },

  {
    path: '',
    component: NavComponent, canActivate: [AutenticacaoGuard], children: [
      {
        path: 'home',
        component: HomeComponent
      },
      
      {
        path: 'tecnicos',
        component: TecnicoListComponent
      },
      {
        path: 'tecnicos/create',
        component: TecnicoCreateComponent
      },
      {
        path: 'tecnicos/update/:id',
        component: TecnicoUpdateComponent
      },
      {
        path: 'tecnicos/delete/:id',
        component: TecnicoDeleteComponent
      }
    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }