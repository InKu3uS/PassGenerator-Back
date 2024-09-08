import { Component, inject, OnInit } from '@angular/core';
import { TitleService } from '../../services/title/title.service';
import { AccountsService } from '../../services/accounts/accounts.service';
import { ExportService } from '../../services/export/export.service';
import { Title } from '@angular/platform-browser';
import { Cuenta, cuentaSchema } from '../../model/cuentaSchema';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-passwords',
  templateUrl: './passwords.component.html',
  styleUrl: './passwords.component.css'
})
export class PasswordsComponent implements OnInit {

  private titleService = inject(TitleService);
  private service = inject(AccountsService);
  private title = inject(Title);
  private export = inject(ExportService);

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

  deleteAccount(site:string) {
    Swal.fire({
      title: "¿Desea borrar esta cuenta?",
      text: "Este cambio es irreversible",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Borrar",
      confirmButtonColor: "#3085d6",
      cancelButtonText: "Cancelar",
      cancelButtonColor: "#d33"
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deleteAccount(site).subscribe({
          next: () => {
            this.accountList = this.accountList.filter(acc => acc.site!== site);
            Swal.fire({
              title: "Borrado",
              text: "La cuenta se ha borrado con éxito.",
              icon: "success"
            });
          },
          error: (error) => {
            console.error(error);
            Swal.fire({
              title: "Error",
              text: "No se ha podido borrar la cuenta",
              icon: "error"
            });
          }
        });
      }
      if (result.isDismissed){
        Swal.fire({
          title: "Cancelado",
          text: "La operación fue cancelada",
          icon: "info"
        });
      }
    });
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

  exportJson(){
    if (!this.accountList || this.accountList.length === 0) {
      console.error('La lista de cuentas está vacía o no está definida.');
      return;
    }
    this.export.exportJson(this.accountList);
  }


  exportExcel() {
    if (!this.accountList || this.accountList.length === 0) {
      console.error('La lista de cuentas está vacía o no está definida.');
      return;
    }
    this.export.exportExcel(this.accountList);
  }

  exportPDF(){
    if (!this.accountList || this.accountList.length === 0) {
      console.error('La lista de cuentas está vacía o no está definida.');
      return;
    }
    this.export.exportPDF(this.accountList);
  }
}
