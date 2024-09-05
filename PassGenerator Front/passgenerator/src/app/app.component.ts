import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from './services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  private route = inject(Router);
  private authService = inject(AuthService);

  title = 'passgenerator';
  ngOnInit(): void {

  }
  //TODO: Guardar rol de usuario al iniciar sesión.
  //TODO: Descargar la lista de cuentas (JSON,CSV, Excel, etc...).
  //TODO: Implementar sistema de correos electronicos.
  //TODO: Funcionalidad para editar contraseñas.
  //TODO: Funcionalidad para borrar contraseñas.
  //TODO: Enviar correo cuando haya contraseña a punto de caducar?
}
