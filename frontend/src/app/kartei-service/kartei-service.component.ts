import {Component, inject, OnInit} from '@angular/core';
import {NgFor, NgIf} from '@angular/common';
import {KarteiApiService} from './kartei.api.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-kartei-service',
  imports: [NgIf, NgFor, RouterLink],
  templateUrl: './kartei-service.component.html',
  standalone: true,
  styleUrl: './kartei-service.component.scss'
})
export class KarteiServiceComponent implements OnInit{

  karteiSetsAvailable : boolean = false

  karteiService = inject(KarteiApiService)

  stapel : any = []

  ngOnInit(): void {
    this.checkSetsAvailable()
    this.getAllStapelByUsername()
  }

  checkSetsAvailable() {
    this.karteiService.checkSetsAvailable().subscribe({
      next: (result) => {
        this.karteiSetsAvailable = result
        console.log(this.karteiSetsAvailable)
      }
    })
  }

  getAllStapelByUsername() {
    this.karteiService.getAllStapelByUsername().subscribe({
      next: (data) => {
        this.stapel = data
        console.log(this.stapel)
      }
    })
  }



}
