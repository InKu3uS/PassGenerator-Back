import { Component, inject, OnInit } from '@angular/core';
import { TitleService } from '../../services/title/title.service';
import { Title } from '@angular/platform-browser';
import { ExportService } from '../../services/export/export.service';
import { emptyUser, userWithoutUuidSchema } from '../../model/UserSchema';
import { UsersService } from '../../services/users/users.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { catchError, map, Observable, of } from 'rxjs';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrl: './my-profile.component.css',
})
export class MyProfileComponent implements OnInit {
  private titleService = inject(TitleService);
  private title = inject(Title);
  private export = inject(ExportService);
  private userService = inject(UsersService);

  emailLoggedIn = '';
  user: emptyUser | undefined;

  defaultTitle: string = 'PassGenerator - Mi Perfil';

  errorEditUsername = '';
  errorEditPassword = '';

  permitUsernameEdit = false;
  permitPasswordEdit = false;

  usernameForm: FormGroup = new FormGroup({
    username: new FormControl(null, Validators.required),
  });

  passwordForm: FormGroup = new FormGroup({
    password: new FormControl(null, Validators.required),
    newPassword: new FormControl(null, [Validators.required, Validators.minLength(8)]),
  });

  ngOnInit(): void {
    this.title.setTitle(this.defaultTitle);
    this.titleService.blurTitle(this.defaultTitle);
    this.getMailLogged();
    this.getUser(this.emailLoggedIn);
  }

  getMailLogged() {
    if (localStorage.getItem('user') != null) {
      this.emailLoggedIn = localStorage.getItem('user')!;
    }
  }

  setFormValues() {
    this.usernameForm.get('username')?.setValue(this.user?.username);
    this.usernameForm.get('username')?.disable();
    this.passwordForm.get('password')?.disable();
    this.passwordForm.get('newPassword')?.disable();
  }

  editUsername() {
    this.permitUsernameEdit = !this.permitUsernameEdit;
    if (this.permitUsernameEdit) {
      this.usernameForm.get('username')?.enable();
      this.passwordForm.get('password')?.disable();
      this.passwordForm.get('newPassword')?.disable();
      this.permitPasswordEdit = false; // Asegurarse de que la contraseña no esté habilitada
    } else {
      this.usernameForm.get('username')?.disable();
    }
  }

  editPassword() {
    this.permitPasswordEdit = !this.permitPasswordEdit;
    if (this.permitPasswordEdit) {
      this.passwordForm.get('password')?.enable();
      this.passwordForm.get('newPassword')?.enable();
      this.usernameForm.get('username')?.disable();
      this.permitUsernameEdit = false; // Asegurarse de que el nombre de usuario no esté habilitado
    } else {
      this.passwordForm.get('password')?.disable();
      this.passwordForm.get('newPassword')?.disable();
    }
  }

  getUser(email: string) {
    this.userService.getUserByEmail(email).subscribe({
      next: (user) => {
        if (user.email && user.password) {
          this.user = userWithoutUuidSchema.parse(user);
          this.setFormValues();
        }
      },
      error: (error) => {
        console.error('Error:', error);
      },
    });
  }

verifyPassword(email: string, password: string): Observable<boolean> {
  return this.userService.verifyPassword(email, password).pipe(
    map((response) => {
      if (response) {
        console.log('Las contraseñas coinciden');
        return true;
      } else {
        this.errorEditPassword = 'La contraseña actual no es correcta';
        return false;
      }
    }),
    catchError((error) => {
      console.error('Error:', error);
      return of(false);
    })
  );
}

  changeUsername(){
    const username = this.usernameForm.get('username')?.value;

    if(username == this.user?.username){
      this.errorEditUsername = 'El nombre de usuario no puede ser el mismo';
      return;
    }

    this.userService.editUsername(this.emailLoggedIn, username).subscribe({
      next: (response) => {
          console.log(response);
          this.getUser(this.emailLoggedIn);
          this.permitUsernameEdit = false;
          //TODO: SweetAlert aqui.
      },
      error: (error) => {
        console.error('Error:', error);
      },
    });
  }

  changePassword() {
    const actualPassword = this.passwordForm.get('password')?.value;
    const newPassword = this.passwordForm.get('newPassword')?.value;
    
    if (newPassword == actualPassword) {
      this.errorEditPassword = 'La nueva contraseña debe ser distinta de la actual';
      return;
    }

    this.verifyPassword(this.emailLoggedIn, actualPassword!).subscribe((isValid) => {
      if (isValid) {
        this.userService.editPassword(this.emailLoggedIn, newPassword).subscribe({
          next: (response) => {
            console.log("Contraseña actualizada con éxito");
            console.log("Respuesta: "+response);
            this.passwordForm.reset();
            this.permitPasswordEdit = false;
            //TODO: SweetAlert aquí.
          },
          error: (error) => {
            console.error('Error al actualizar la contraseña:', error);
            //TODO: SweetAlert aquí.
          }
        });
      }
    });
  }
}
