import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink, RouterOutlet} from '@angular/router';
import {Stapel} from '../domain';
import {KarteiApiService} from '../kartei.api.service';
import {NgFor, NgIf} from '@angular/common';
import {KarteErstellenComponent} from '../karte-erstellen/karte-erstellen.component';

@Component({
  selector: 'app-stapel-details',
  imports: [NgFor, NgIf, KarteErstellenComponent],
  templateUrl: './stapel-details.component.html',
  standalone: true,
  styleUrl: './stapel-details.component.scss'
})
export class StapelDetailsComponent implements OnInit{

  showForm = false

  stapel : Stapel = {}
  stapelId: string | null = ''

  route = inject(ActivatedRoute)
  karteiService = inject(KarteiApiService)

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => { this.stapelId = params.get('fachId'); });

    this.karteiService.getStapelByFachId(this.stapelId).subscribe({
      next: (data : Stapel) => {
        this.stapel = data
      }
    })
  }


  showKarteErstellenForm() {
    this.showForm = !this.showForm
  }
}
