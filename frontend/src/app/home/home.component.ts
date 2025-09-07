import {Component, inject, OnInit} from '@angular/core';
import {jwtDecode} from 'jwt-decode';
import {HomeService, UserServiceInformation} from './home.service';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {PlanApiService} from '../plan-service/plan-api.service';
import {MatProgressSpinner} from '@angular/material/progress-spinner';

@Component({
  selector: 'app-home',
  imports: [NgIf, RouterLink, MatProgressSpinner],
  templateUrl: './home.component.html',
  standalone: true,
  styleUrls: ['./home.component.scss', '../general.scss']
})
export class HomeComponent implements OnInit{
  homeService = inject(HomeService)
  planApiService = inject(PlanApiService)
  isLoading = true;
  todayPlanned = false

  username: string = "default";
  userServiceInformation : UserServiceInformation = {} as UserServiceInformation

  ngOnInit(): void {
    this.getUsername()
    this.homeService.gatherUserServiceInformation().subscribe(usi => {
      this.userServiceInformation = usi;
      this.isTodayPlanned()
      this.isLoading = false
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

  isTodayPlanned() {
    this.planApiService.isTodayPlanned().subscribe({
      next: data => {
        this.todayPlanned = data
      }
    })
  }
}
