import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private title:Title){
    window.addEventListener('focus', event => {
      this.title.setTitle('Inicio');
    });
    window.addEventListener('blur', event => {
      setTimeout(() => {
        this.title.setTitle('Hey! Sigues ahi?');
      }, 30000);
      
      
    });
  }



}
