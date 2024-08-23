import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cuenta } from '../../model/cuentaSchema';
import { Observable } from 'rxjs';
import { Router, RouterLink } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(private http:HttpClient, private router:Router) { }

  private apiUrl = 'http://localhost:8080/cuentas';
  private token = localStorage.getItem('token');
  private headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.token);
  private generatedPassword:string = "";

  getAllAccounts(): Observable<Cuenta[]> {
    return this.http.get<Cuenta[]>(`${this.apiUrl}/list`, {headers: this.headers});
  }

  getAllAccountsByUser(mail:string): Observable<Cuenta[]> {
    return this.http.get<Cuenta[]>(`${this.apiUrl}/user/${mail}`, {headers: this.headers});
  }

  savePassword(password:string) {
    this.generatedPassword = password;
  }

  getPassword(){
    return this.generatedPassword;
  }

  save(cuenta:Cuenta): Observable<any>{
    return this.http.post(`${this.apiUrl}/save`, cuenta, {headers:this.headers, responseType: 'json', observe: 'response'});
  }
}
