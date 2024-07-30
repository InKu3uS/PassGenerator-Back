import { Component, inject, Injectable, OnChanges, OnInit, SimpleChanges, TemplateRef, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit{


  private route = inject(Router);
  private authService = inject(AuthService);

  shodDropdown = false;
  showMobileMenu = false;
  isLoggedIn = false;

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
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


}
