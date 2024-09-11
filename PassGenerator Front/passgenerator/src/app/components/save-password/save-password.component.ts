import { Component, inject, Input, OnInit } from '@angular/core';
import { AccountsService } from '../../services/accounts/accounts.service';
import { UsersService } from '../../services/users/users.service';
import { User } from '../../model/UserSchema';
import { createCuenta, Cuenta } from '../../model/cuentaSchema';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SwalService } from '../../services/swal/swal.service';

@Component({
  selector: 'app-save-password',
  templateUrl: './save-password.component.html',
  styleUrl: './save-password.component.css'
})
export class SavePasswordComponent implements OnInit {

  
  user: User | undefined;
  private service = inject(AccountsService);
  private userService = inject(UsersService);
  private swal = inject(SwalService);

  password: string = "";

  cuentaForm: FormGroup = new FormGroup({
    site: new FormControl(null, Validators.required),
    password: new FormControl(null, Validators.required),
    expiration: new FormControl(null, Validators.required)
  });
  
  cuenta = createCuenta;

  ngOnInit(): void {
    if(this.service.getPassword() !== ""){
      this.password = this.service.getPassword();
      this.existsPassword();
    }
    this.getUserLogged();
  }

  existsPassword(){
    this.cuentaForm.get('password')?.setValue(this.password);
    this.cuentaForm.get('password')?.disable();
  }

  getUserLogged(){
    let mail = localStorage.getItem('user');
    if(mail != null){
      this.userService.getUserByEmail(mail).subscribe({
        next: (user) => {
          this.user = user;
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }
  saveAccount(){
    let site = this.cuentaForm.get('site')?.value;
    let password = this.cuentaForm.get('password')?.value;
    let expiration = this.cuentaForm.get('expiration')?.value;
    
    if(expiration){
      expiration = expiration.split('-').reverse().join('/');
    }
    
    let cuenta: Cuenta = {
      user:{
        uuid: this.user?.uuid ?? ""
      },
      createTime: new Date().toISOString(),
      expirationTime: expiration,
      site: site,
      password: password
    };
    const validationResult = createCuenta.safeParse(cuenta);
    if (!validationResult.success) {
        console.error("Validation error:", validationResult.error);
        return;
    }
    this.service.save(cuenta).subscribe({
      next: (response) => {
        this.cuentaForm.reset();
        this.swal.savedAccount(site);
        this.service.resetPassword();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }
}
