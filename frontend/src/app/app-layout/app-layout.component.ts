import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink, RouterOutlet} from '@angular/router';
import * as jwt from 'jwt-decode';
import { LoggingService } from '../logging.service';

@Component({
  selector: 'app-app-layout',
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './app-layout.component.html',
  standalone: true,
  styleUrls: ['./app-layout.component.scss', '../general.scss', 'side-navbar.scss']
})
export class AppLayoutComponent implements OnInit{
  log : LoggingService = new LoggingService("AppComponent", "app")

  isAdmin : boolean = false
  isLoggedIn : boolean = false

  checkAdmin(token : string) : boolean {
    this.log.debug("Check if user is admin...")
    if(token != null) {
      const decoded: any = jwt.jwtDecode(token)
      const authorities = decoded.authorities || [];

      let res = !!authorities.includes('ROLE_ADMIN')

      if(res) {
        this.log.debug("User is admin...")
        return res
      } else {
        this.log.debug("User is NOT admin...")
      }
    } else if (token == null || token == '') {
      this.log.debug("Invalid auth token / auth token not found")
      return false
    }
    return false
  }

  ngOnInit(): void {
    const authToken : string = localStorage.getItem("auth_token") ?? ''
    this.isAdmin = this.checkAdmin(authToken)
    this.isLoggedIn = this.checkLogin(authToken)
  }

  checkLogin(authToken: string): boolean {
    this.log.debug("Check if user is logged in...")

    let res = authToken != null || authToken != ''

    if(res) {
      this.log.debug("User is logged in...")
      return authToken != null || authToken != ''
    } else {
      this.log.debug("User is NOT logged in...")
      return authToken != null || authToken != ''
    }
  }
}
