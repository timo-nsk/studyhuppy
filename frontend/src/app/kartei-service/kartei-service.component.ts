import {Component, inject, OnInit} from '@angular/core';
import {NgFor, NgIf} from '@angular/common';
import {KarteiApiService} from './kartei.api.service';
import {RouterLink} from '@angular/router';
import {MatProgressBar} from "@angular/material/progress-bar";
import { LernzeitFormatPipe } from './lernzeit-format.pipe';
import {LoggingService} from '../logging.service';
import {Stapel} from './domain';

@Component({
  selector: 'app-kartei-service',
  imports: [NgIf, NgFor, RouterLink, MatProgressBar, LernzeitFormatPipe],
  templateUrl: './kartei-service.component.html',
  standalone: true,
  styleUrls: ['./kartei-service.component.scss', '../loading.scss', '../color.scss']
})
export class KarteiServiceComponent implements OnInit{
  log = new LoggingService("KarteiServiceComponent", "kartei-service")
  karteiService = inject(KarteiApiService)
  isLoading : boolean = true
  karteiSetsAvailable : boolean = false
  stapel : Stapel[] = []

  ngOnInit(): void {
    this.checkSetsAvailable()
    this.getAllStapelByUsername()
  }

  checkSetsAvailable() {
    this.karteiService.checkSetsAvailable().subscribe({
      next: (result) => {
        this.karteiSetsAvailable = result
        this.log.info(`got check sets available: ${result}`)
      },
      error: err => {
        this.log.error(`error getting result checking sets are available. reason: ${err}`)
      }
    })
  }

  getAllStapelByUsername() {
    this.karteiService.getAllStapelByUsername().subscribe({
      next: (data) => {
        this.stapel = data
        this.isLoading = false
        this.log.debug(`got stapel by username: ${data}`)
      },
      error: err => {
        this.log.error(`error getting stapel by username. reason: ${err}`)
      }
    })
  }
}
