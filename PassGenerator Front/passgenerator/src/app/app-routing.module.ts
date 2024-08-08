import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PasswordsComponent } from './components/passwords/passwords.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { LoginComponent } from './components/login/login.component';
import { GeneratePasswordComponent } from './components/generate-password/generate-password.component';
import { SavePasswordComponent } from './components/save-password/save-password.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  //{ path: '**', redirectTo: 'home' }, // For any unmatched routes, redirect to home page
  { path: 'home', component: HomeComponent},
  { path: 'passwords', component: PasswordsComponent},
  { path: 'users/list', component: UserListComponent},
  { path: 'login', component: LoginComponent},
  { path: 'generate', component: GeneratePasswordComponent},
  { path: 'save', component: SavePasswordComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
