import {Component, inject, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {SessionApiService, SessionBewertungGeneralStatistik} from '../../session-api.service';
import {SessionInfoDto} from '../../session-domain';
import {NgForOf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {LineChartComponent} from './chart/chart.component';

@Component({
  selector: 'app-bewertung-statistiken',
  imports: [
    RouterLink,
    NgForOf,
    FormsModule,
    LineChartComponent
  ],
  templateUrl: './bewertung-statistiken.component.html',
  styleUrl: './bewertung-statistiken.component.scss'
})
export class SessionBewertungStatistikenComponent implements OnInit{
  sessionApiService : SessionApiService = inject(SessionApiService);
  generalStatistiken : SessionBewertungGeneralStatistik = {} as SessionBewertungGeneralStatistik
  sessionInfo : SessionInfoDto[] = []
  selectedSessionId!: string;

  ngOnInit(): void {
    this.sessionApiService.getGeneralBewertungStatistiken().subscribe({
      next: data => {
        this.generalStatistiken = data;
        console.log("Fetched general statistik: ", this.generalStatistiken);
      }
    });
    this.sessionApiService.getLernplanSessionData().subscribe({
      next: data => {
        this.sessionInfo = data;
        this.selectedSessionId = this.sessionInfo[0].fachId
        console.log("Fetched general statistik: ", this.sessionInfo);
      }
    })
  }
}
