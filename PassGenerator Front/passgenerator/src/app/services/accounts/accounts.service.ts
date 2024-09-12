import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cuenta } from '../../model/cuentaSchema';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

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

  countAllAccountsByEmail(email:string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/${email}`, {headers: this.headers});
  }

  deleteAccount(site:string): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/${site}`, {headers: this.headers});
  }

  savePassword(password:string) {
    this.generatedPassword = password;
  }

  getPassword(){
    return this.generatedPassword;
  }

  resetPassword(){
    this.generatedPassword = "";
  }

  save(cuenta:Cuenta): Observable<any>{
    return this.http.post(`${this.apiUrl}/save`, cuenta, {headers:this.headers, responseType: 'json', observe: 'response'});
  }
}
