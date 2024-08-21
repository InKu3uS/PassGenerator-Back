import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PasswordsComponent } from './components/passwords/passwords.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { LoginComponent } from './components/login/login.component';
import { GeneratePasswordComponent } from './components/generate-password/generate-password.component';
import { SavePasswordComponent } from './components/save-password/save-password.component';
import { FaqComponent } from './components/faq/faq.component';
import { authGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  //{ path: '**', redirectTo: 'home' }, // For any unmatched routes, redirect to home page
  { path: 'home', component: HomeComponent},
  { path: 'passwords', component: PasswordsComponent, canActivate: [authGuard]},
  { path: 'users/list', component: UserListComponent, canActivate: [authGuard]},
  { path: 'login', component: LoginComponent},
  { path: 'generate', component: GeneratePasswordComponent},
  { path: 'save', component: SavePasswordComponent, canActivate: [authGuard] },
  { path: 'faq', component: FaqComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
