import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cuenta } from '../../model/cuentaSchema';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(private http:HttpClient) { }

  private apiUrl = 'http://localhost:8080/cuentas';

  getAllAccounts(): Observable<Cuenta[]> {
    return this.http.get<Cuenta[]>(`${this.apiUrl}/list`);
  }
}
