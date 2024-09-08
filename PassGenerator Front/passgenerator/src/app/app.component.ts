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
  //TODO: Arreglar problema responsive con login.
  //TODO: Mover la funcionalidad de exportaciones a un servicio a parte.
  //TODO: Implementacion de componente 'Mi Perfil'.
  //TODO: Implementacion de cambio de contraseña para la cuenta de usuario.
  //TODO: Implementacion de borrado de cuenta con SweetAlert2.
  //TODO: Guardar rol de usuario al iniciar sesión.
  //TODO: Funcionalidad para editar contraseñas.
  //TODO: Enviar correo cuando haya contraseña a punto de caducar?
}
