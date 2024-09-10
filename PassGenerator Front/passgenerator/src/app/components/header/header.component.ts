import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { User } from '../../model/UserSchema';
import { UsersService } from '../../services/users/users.service';

@Component({
  selector: 'header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit{


  private route = inject(Router);
  private authService = inject(AuthService);
  private userService = inject(UsersService);

  shodDropdown = false;
  showMobileMenu = false;
  isLoggedIn = false;
  username = '';

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
    if(this.isLoggedIn){
      this.getUserName();
    }
  }

  changeLogin(){
    this.isLoggedIn = this.authService.isLoggedIn();
  }


  toggleDropdown() {
    this.shodDropdown =!this.shodDropdown;
  }

  toggleMobileMenu() {
    this.showMobileMenu =!this.showMobileMenu;
  }

  logout() {
    this.authService.logout();
    this.route.navigate(['/home']);
  }

  getUserName() {
    let email = localStorage.getItem('user');
    if(email!= null){
      this.userService.getUserByEmail(email).subscribe({
        next: (user) => {
          this.username = user.username;
        },
        error: (error) => {
          console.log(error);
        }
      });
    } 
  }
}
