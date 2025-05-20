import {Component, inject, OnInit} from '@angular/core';
import {NgFor, NgIf} from '@angular/common';
import {KarteiApiService} from './kartei.api.service';
import {RouterLink} from '@angular/router';
import {MatProgressBar} from "@angular/material/progress-bar";

@Component({
  selector: 'app-kartei-service',
    imports: [NgIf, NgFor, RouterLink, MatProgressBar],
  templateUrl: './kartei-service.component.html',
  standalone: true,
  styleUrls: ['./kartei-service.component.scss', '../loading.scss']
})
export class KarteiServiceComponent implements OnInit{
  isLoading : boolean = true
  karteiSetsAvailable : boolean = false

  karteiService = inject(KarteiApiService)

  stapel : any = []
  anzahlFaelligeKarten : number | undefined;

  ngOnInit(): void {
    this.checkSetsAvailable()
    this.getAllStapelByUsername()
  }

  checkSetsAvailable() {
    this.karteiService.checkSetsAvailable().subscribe({
      next: (result) => {
        this.karteiSetsAvailable = result
      }
    })
  }

  getAllStapelByUsername() {
    this.karteiService.getAllStapelByUsername().subscribe({
      next: (data) => {
        this.stapel = data
        this.isLoading = false
      }
    })
  }



}
