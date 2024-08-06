import { Component } from '@angular/core';

@Component({
  selector: 'generate-password',
  templateUrl: './generate-password.component.html',
  styleUrl: './generate-password.component.css'
})
export class GeneratePasswordComponent {

  passwordLength: number = 25;

  /**
   * Obtiene el valor del slide '#length-range' y lo guarda el 'passwordLength'.
   */
  GetRangeValue(){
    let actualLength = document.querySelector('#length-range') as HTMLInputElement;
    this.passwordLength = parseInt(actualLength.value);
  }


  /**
   * Incrementa el valor del slide '#length-range' en 1 actualiza 'passwordLength'
   */
  increaseRange(){
    let actualLength = document.querySelector('#length-range') as HTMLInputElement;
    this.passwordLength++;
    actualLength.value = this.passwordLength.toString();
  }

  /**
   * Reduce el valor del slide '#length-range' en 1 actualiza 'passwordLength'
   */
  decreaseRange(){
    let actualLength = document.querySelector('#length-range') as HTMLInputElement;
    this.passwordLength--;
    actualLength.value = this.passwordLength.toString();
  }
}
