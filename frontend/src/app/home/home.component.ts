import {Component, inject, OnInit} from '@angular/core';
import {jwtDecode} from 'jwt-decode';
import {HomeService, UserServiceInformation} from './home.service';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [NgIf, RouterLink],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss', '../general.scss']
})
export class HomeComponent implements OnInit{
  homeService = inject(HomeService)

  username: string = "default";
  userServiceInformation : UserServiceInformation = {} as UserServiceInformation

  ngOnInit(): void {
    this.getUsername()
    this.homeService.gatherUserServiceInformation().subscribe(usi => {
      this.userServiceInformation = usi;
    });
  }

  getUsername() {
    const token = localStorage.getItem("auth_token");

    if (token != null) {
      const decoded = jwtDecode(token)
      this.username =  decoded.sub ?? 'none'
    }
  }

  hasModule() {
    return this.userServiceInformation.hasModule
  }

  hasLernSessions() {
    return this.userServiceInformation.hasLernSessions
  }

  hasLernplan() {
    return this.userServiceInformation.hasLernplan
  }

  hasKarteikartenstapel() {
    return this.userServiceInformation.hasKarteikartenStapel
  }
}
