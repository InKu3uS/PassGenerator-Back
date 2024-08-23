import { Component, inject, OnInit } from '@angular/core';
import { TitleService } from '../../services/title/title.service';
import { Title } from '@angular/platform-browser';
import { UsersService } from '../../services/users/users.service';
import { User, userSchema } from '../../model/UserSchema';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit {

  private titleService = inject(TitleService);
  private service = inject(UsersService);
  private title = inject(Title);

  private defaultTitle: string = 'PassGenerator - Lista de Usuarios';
  userList: User[] = [];

  ngOnInit(): void {
    this.title.setTitle(this.defaultTitle);
    this.titleService.blurTitle(this.defaultTitle);
    this.getUsers();
  }

  /**
   * Recoge la lista de usuarios del Backend y la guarda una lista
   * @params ninguno
   * @returns void
   * @description Llama al servicio `UserService` para obtener la lista de usuarios
   *  luego procesa cada usuario parseandolos usando `userSchema.safeParse()`.
   *  Si el usuario es parseado con éxito se añade a `userList`.
   *  Si el parseo falla, se muestra un error y se pasa al siguiente usuario de la lista. 
   *  Una vez se han procesado todos los usuarios, se muestra un mensaje y se muestra la lista de usuarios
   *  por consola en forma de tabla.
   */
  getUsers(): void {
    this.service.getAllUsers().subscribe({
      next: (users) => {
        users.forEach((user) => {
          const userParsed = userSchema.safeParse(user);
          if(userParsed.success){
            this.userList.push(userParsed.data);
          }else{
            console.error('Invalid user:', userParsed.error);
          }
        });
      },
      error: (error) => {
        console.error('Error retrieving users:', error);
      }
    });
  }

}
