import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../model/UserSchema';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient, private router:Router) { }

  private apiUrl = 'http://localhost:8080/auth';

  login(mail: string, password: string): Observable<any>{
    return this.http.post(`${this.apiUrl}/login`, { mail, password });
  }

  register(user:User): Observable<any>{
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  isLoggedIn(): boolean{
    const token = localStorage.getItem('token');
    return token!=null;
  }

  logout(): void{
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.router.navigate(['/home']);
    location.reload();
  }
}
