import {Component, inject, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {KarteiApiService} from './kartei.api.service';

@Component({
  selector: 'app-kartei-service',
  imports: [NgIf],
  templateUrl: './kartei-service.component.html',
  standalone: true,
  styleUrl: './kartei-service.component.scss'
})
export class KarteiServiceComponent implements OnInit{

  karteiSetsAvailable : boolean = false

  karteiService = inject(KarteiApiService)

  ngOnInit(): void {
    this.checkSetsAvailable()
  }

  checkSetsAvailable() {
    this.karteiService.checkSetsAvailable().subscribe({
      next: (result) => {
        this.karteiSetsAvailable = result
        console.log(this.karteiSetsAvailable)
      }
    })
  }



}
