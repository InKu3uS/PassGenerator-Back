import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class SwalService {

  constructor() { }

  registeredUser(username: string){
    Swal.fire({
      position: "bottom-end",
      icon: "success",
      title: "Usuario registrado",
      text: `Bienvenido a PassGenerator ${username}`,
      showConfirmButton: false,
      timer: 1500
    });
  }

  savedAccount(site: string){
    Swal.fire({
      position: "bottom-end",
      icon: "success",
      title: "Cuenta guardada",
      text: `La contraseña de ${site} se ha registrado con éxito`,
      showConfirmButton: false,
      timer: 1500
    });
  }
}
