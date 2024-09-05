import { Component, inject, OnInit } from '@angular/core';
import { TitleService } from '../../services/title/title.service';
import { AccountsService } from '../../services/accounts/accounts.service';
import { Title } from '@angular/platform-browser';
import { Cuenta, cuentaSchema } from '../../model/cuentaSchema';

@Component({
  selector: 'app-passwords',
  templateUrl: './passwords.component.html',
  styleUrl: './passwords.component.css'
})
export class PasswordsComponent implements OnInit {

  private titleService = inject(TitleService);
  private service = inject(AccountsService);
  private title = inject(Title);

  defaultTitle: string = 'PassGenerator - Passwords';
  accountList: Cuenta[] = [];
  userLoggedIn: string  = localStorage.getItem('user') || '';
  actualDate: string = '';

  ngOnInit(): void {
    this.title.setTitle(this.defaultTitle);
    this.titleService.blurTitle(this.defaultTitle);
    this.getFechaActual();
    this.getAll();
  }

  showPassword(index:number): void {
      let input: HTMLInputElement | null = document.querySelector('#password'+index);
      if (input) {
        input.setAttribute('type', 'text');
      }
  }

  hidePassword(index:number): void {
    let input: HTMLInputElement | null = document.querySelector('#password'+index);
      if (input) {
        input.setAttribute('type', 'password');
      }
  }

  getFechaActual(){
    let today = new Date();
    this.actualDate = today.toLocaleDateString();
  }


  getAll():void {
    if (this.userLoggedIn == '') {
      console.error('No sesión de usuario iniciada');
      return;
    }
    this.service.getAllAccountsByUser(this.userLoggedIn).subscribe({
      next: (accounts) => {
        accounts.forEach((account) => {
          const accountsParsed = cuentaSchema.safeParse(account);
          if(accountsParsed.success){
            this.accountList.push(accountsParsed.data);
          }else{
            console.error('Invalid account:', accountsParsed.error);
          }
        })
      },
      error: (error) => {
        console.error('Error getting accounts:', error);
      }
    });
  }
}
