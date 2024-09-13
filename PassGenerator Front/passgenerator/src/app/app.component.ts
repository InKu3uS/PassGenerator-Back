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
  //TODO: Ocultar Lista de usuario del header para los usuarios normales.
  //TODO: Revisar todas las excepciones en el back.
  //TODO: Enviar correo cuando haya contraseña a punto de caducar?
  //TODO: Guardar rol de usuario al iniciar sesión?.
}
