import { Component, inject, OnInit } from '@angular/core';
import { TitleService } from '../../services/title/title.service';
import { Title } from '@angular/platform-browser';
import { AuthService } from '../../services/auth/auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  private titleService = inject(TitleService);
  private route = inject(Router);
  private service = inject(AuthService);
  private title = inject(Title);

  private defaultTitle: string = 'PassGenerator - Login';

  ngOnInit(): void {
    this.title.setTitle(this.defaultTitle);
    this.titleService.blurTitle(this.defaultTitle);

    this.redirectIfLoggedIn();
  }

  login(): void {
    const helper = new JwtHelperService();
    const mail = (document.getElementById('email') as HTMLInputElement)
      .value;
    const password = (document.getElementById('password') as HTMLInputElement)
      .value;

    this.service.login(mail, password).subscribe({
      next: (session) => {
        console.log('Logged in successfully');
        localStorage.setItem('token', session.token);
        const decodedToken = helper.decodeToken(session.token);
        console.log(decodedToken);
        localStorage.setItem('user', decodedToken.sub);
        this.route.navigate(['/home']).then(()=> {location.reload()});
      },
      error: (error) => {
        if(error.status === 403){
          console.log('Invalid credentials. Please try again.');
        }else{
          console.error('Error logging in', error);
        }
      },
    });
  }

  redirectIfLoggedIn() {
    if (this.service.isLoggedIn()) {
      this.route.navigate(['/home']);
    }
  }
}
