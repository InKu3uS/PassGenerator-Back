import { Component, inject, OnInit } from '@angular/core';
import { TitleService } from '../../services/title/title.service';
import { Title } from '@angular/platform-browser';
import { AuthService } from '../../services/auth/auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  private titleService = inject(TitleService);
  private service = inject(AuthService);
  private title = inject(Title);

  private defaultTitle: string = 'PassGenerator - Login';
  

  ngOnInit(): void {
    this.title.setTitle(this.defaultTitle);
    this.titleService.blurTitle(this.defaultTitle);
  }

  login(): void {
    const helper = new JwtHelperService();
    const username = (document.getElementById('email') as HTMLInputElement).value
    const password = (document.getElementById('password') as HTMLInputElement).value;

    this.service.login(username, password).subscribe({
      next: (session) => {
        console.log('Logged in successfully');
        localStorage.setItem('token', session.token);
        const decodedToken = helper.decodeToken(session.token);
        localStorage.setItem('user', decodedToken.sub);
        console.log(decodedToken);
        // TODO: Redireccionar al dashboard
      }, error: (error) => {
        console.error('Error logging in', error);
        // TODO: Mostrar mensaje de error al usuario
      }
    });
  }
}
