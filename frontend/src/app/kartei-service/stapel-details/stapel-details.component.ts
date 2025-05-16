import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Stapel} from '../domain';
import {KarteiApiService} from '../kartei.api.service';
import {NgFor} from '@angular/common';

@Component({
  selector: 'app-stapel-details',
  imports: [NgFor],
  templateUrl: './stapel-details.component.html',
  standalone: true,
  styleUrl: './stapel-details.component.scss'
})
export class StapelDetailsComponent implements OnInit{

  stapel : Stapel = {}

  route = inject(ActivatedRoute)
  karteiService = inject(KarteiApiService)

  ngOnInit(): void {
    let stapelId: string | null = ''
    this.route.paramMap.subscribe(params => { stapelId = params.get('fachId'); });

    this.karteiService.getStapelByFachId(stapelId).subscribe({
      next: (data : Stapel) => {
        this.stapel = data
      }
    })
  }



}
