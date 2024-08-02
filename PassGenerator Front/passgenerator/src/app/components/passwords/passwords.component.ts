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
  AccountList: Cuenta[] = [];

  ngOnInit(): void {
    this.title.setTitle(this.defaultTitle);
    this.titleService.blurTitle(this.defaultTitle);
    this.getAll();
  }

  getAll():void {
    this.service.getAllAccounts().subscribe({
      next: (accounts) => {
        console.table(accounts);
        this.AccountList = accounts;
        console.log(this.AccountList);
        
      },
      error: (error) => {
        console.error('Error getting accounts:', error);
      }
    })
      
  }

}
