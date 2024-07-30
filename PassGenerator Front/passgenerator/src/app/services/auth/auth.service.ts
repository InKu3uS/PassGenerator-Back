import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }

  private apiUrl = 'http://localhost:8080/auth';

  login(username: string, password: string): Observable<any>{
    return this.http.post(`${this.apiUrl}/login`, { username, password });
  }

  isLoggedIn(): boolean{
    const token = localStorage.getItem('token');
    return token!=null;
  }

  logout(): void{
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }
}
