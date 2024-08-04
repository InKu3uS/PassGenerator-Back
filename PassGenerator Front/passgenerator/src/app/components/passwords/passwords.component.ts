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

  ngOnInit(): void {
    this.title.setTitle(this.defaultTitle);
    this.titleService.blurTitle(this.defaultTitle);
    this.getAll();
  }

  getAll():void {
    this.service.getAllAccounts().subscribe({
      next: (accounts) => {
        console.table(accounts);
        accounts.forEach((account) => {
          console.log(account);
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
    })
      
  }

}
