import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PasswordsComponent } from './components/passwords/passwords.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  //{ path: '**', redirectTo: 'home' }, // For any unmatched routes, redirect to home page
  { path: 'home', component: HomeComponent},
  { path: 'passwords', component: PasswordsComponent},

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
