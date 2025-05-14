import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {KarteiApiService} from '../kartei.api.service';
import {Stapel} from '../domain';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-lernen',
  imports: [NgIf],
  templateUrl: './lernen.component.html',
  standalone: true,
  styleUrl: './lernen.component.scss'
})
export class LernenComponent implements OnInit {
  hideAntwort : boolean = true
  hideAntwortBtn : boolean = true
  hideBtnGroup : boolean = false

  route = inject(ActivatedRoute)
  karteiService = inject(KarteiApiService)
  thisStapel : Stapel = {};

  ngOnInit(): void {
    let id: string | null = '';
    this.route.paramMap.subscribe(params => { id = params.get('fachId'); });
    console.log(id)
    this.karteiService.getStapelByFachId(id).subscribe({
      next: (data : Stapel) => {
        this.thisStapel = data
        console.log(this.thisStapel)
      }
    })
  }


  toggleAntwort() {
    this.hideAntwort = false
    this.hideAntwortBtn = false
    this.hideBtnGroup = true
  }
}
