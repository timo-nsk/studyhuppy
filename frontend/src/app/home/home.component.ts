import {Component, inject, OnInit} from '@angular/core';
import {jwtDecode} from 'jwt-decode';
import {HomeService, UserModulServiceInformation, UserKarteiServiceInformation} from './home.service';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {PlanApiService} from '../plan-service/plan-api.service';
import {LoadingDataComponent} from '../app-layout/loading-data/loading-data.component';

@Component({
  selector: 'app-home',
  imports: [NgIf, RouterLink, LoadingDataComponent],
  templateUrl: './home.component.html',
  standalone: true,
  styleUrls: ['./home.component.scss', '../general.scss']
})
export class HomeComponent implements OnInit{
  homeService = inject(HomeService)
  planApiService = inject(PlanApiService)
  isModulDataLoading = true;
  isKarteiDataLoading = true;
  todayPlanned = false

  username: string = "default";
  userServiceInformation : UserModulServiceInformation = {} as UserModulServiceInformation
  userKarteiServiceInfo : UserKarteiServiceInformation = {} as UserKarteiServiceInformation

  ngOnInit(): void {
    this.getUsername()
    this.homeService.gatherUserServiceInformation().subscribe(usi => {
      this.userServiceInformation = usi;
      this.isTodayPlanned()
      this.isModulDataLoading = false
    });
    this.homeService.gatherUserKarteiServiceInformation().subscribe(usi => {
      this.userKarteiServiceInfo = usi;
      this.isKarteiDataLoading = false
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
    return this.userKarteiServiceInfo.hasKarteikartenStapel
  }

  hasFaelligeStapel() {
    return this.userKarteiServiceInfo.faelligeStapel.length > 0
  }

  getFaelligeStapel() {
    if (this.hasFaelligeStapel()) {
      return this.userKarteiServiceInfo.faelligeStapel.join(", ")
    } else {
      return "Keine fälligen Stapel."
    }
  }


  isTodayPlanned() {
    this.planApiService.isTodayPlanned().subscribe({
      next: data => {
        this.todayPlanned = data
      }
    })
  }
}
