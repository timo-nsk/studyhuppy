import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Modul} from '../modul';
import {Kreditpunkte} from '../kreditpunkte';
import {Lerntage} from '../lerntage';

@Component({
  selector: 'app-modul-details',
  imports: [],
  templateUrl: './modul-details.component.html',
  standalone: true,
  styleUrl: './modul-details.component.scss'
})
export class ModulDetailsComponent implements  OnInit{

  route = inject(ActivatedRoute)
  name!: string
  secondsLearned!: number
  kreditpunkte!: Kreditpunkte
  active!: boolean
  semesterstufe!: number
  semester!: string
  klausurDate!: Date
  lerntage: Lerntage


  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.name = params['name']
      this.secondsLearned = params['secondsLearned']
      this.kreditpunkte = params['kreditpunkte']
      this.active = params['active']
      this.semesterstufe = params['semesterstufe']
      this.semester = params['semester']
      this.klausurDate = params['klausurDate']
      this.lerntage = params['lerntage']

      console.log("name: " + this.name)
      console.log("seconds: " + this.secondsLearned)
      console.log("kp: " + this.kreditpunkte)
      console.log("active: " + this.active)
      console.log("semesterstufe: " + this.semesterstufe)
      console.log("semester: " + this.semester)
      console.log("klausur: " + this.klausurDate)
      console.log("lernage:" + this.lerntage)
    });

  }

}
