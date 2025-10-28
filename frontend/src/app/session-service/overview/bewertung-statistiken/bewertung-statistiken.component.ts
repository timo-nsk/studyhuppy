import {Component, inject, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {SessionApiService, SessionBewertungGeneralStatistik} from '../../session-api.service';

@Component({
  selector: 'app-bewertung-statistiken',
  imports: [
    RouterLink
  ],
  templateUrl: './bewertung-statistiken.component.html',
  styleUrl: './bewertung-statistiken.component.scss'
})
export class SessionBewertungStatistikenComponent implements OnInit{
  sessionApiService : SessionApiService = inject(SessionApiService);
  generalStatistiken : SessionBewertungGeneralStatistik = {} as SessionBewertungGeneralStatistik

  ngOnInit(): void {
    this.sessionApiService.getGeneralBewertungStatistiken().subscribe({
      next: data => {
        this.generalStatistiken = data;
        console.log("Fetched general statistik: ", this.generalStatistiken);
      }
    });
  }
}
