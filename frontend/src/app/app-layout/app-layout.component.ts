import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink, RouterOutlet} from '@angular/router';
import {FooterComponent} from './footer/footer.component';
import {HeaderMainComponent} from './header-main/header-main.component';
import * as jwt from 'jwt-decode';

@Component({
  selector: 'app-app-layout',
  imports: [CommonModule, RouterOutlet, RouterLink, FooterComponent, HeaderMainComponent],
  templateUrl: './app-layout.component.html',
  standalone: true,
  styleUrls: ['./app-layout.component.scss', '../general.scss', 'side-navbar.scss']
})
export class AppLayoutComponent implements OnInit{
  isAdmin : boolean = false

  checkAdmin() : boolean {
    let token = localStorage.getItem('auth_token') ?? ''
    const decoded: any = jwt.jwtDecode(token)
    const authorities = decoded.authorities || [];

    return !!authorities.includes('ROLE_ADMIN');
  }

  ngOnInit(): void {
    this.isAdmin = this.checkAdmin()
  }

}
