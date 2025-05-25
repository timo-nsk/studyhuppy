import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TimeFormatPipe} from '../time-format.pipe';
import {DatePipe, NgIf} from '@angular/common';
import {CapitalizePipe} from '../capitalize.pipe';
import {Lerntage} from '../domain';

@Component({
  selector: 'app-modul-details',
  imports: [
    NgIf,
    TimeFormatPipe,
    DatePipe,
    CapitalizePipe
  ],
  templateUrl: './modul-details.component.html',
  standalone: true,
  styleUrls: ['./modul-details.component.scss', '../../../general.scss', '../../../button.scss', '../../../color.scss']
})
export class ModulDetailsComponent implements  OnInit{
  name!: string
  secondsLearned!: number
  kreditpunkte!: number
  kontaktzeitStunden!: number
  selbststudiumStunden!: number
  semesterstufe!: number
  semesterTyp!: string
  semesterJahr: string = '2000'
  klausurDate!: Date | null
  lerntage!: Lerntage

  route = inject(ActivatedRoute)


  ngOnInit(): void {
    this.initComponentWithQueryParamData()

  }

  initComponentWithQueryParamData() {
    this.route.queryParams.subscribe(params => {
      this.name = params['name']
      this.secondsLearned = params['secondsLearned']
      this.kreditpunkte = params['kreditpunkte']
      this.kontaktzeitStunden = params['kontaktzeitStunden']
      this.selbststudiumStunden = params['selbststudiumStunden']
      this.semesterstufe = params['semesterstufe']
      this.semesterTyp = params['semesterTyp']
      this.semesterJahr = this.getSemesterJahr(this.semesterTyp)
      this.klausurDate = params['klausurDate']
      this.lerntage = params['lerntage']

      this.queryParamLog()
    });
  }

  queryParamLog() {
    console.log("name: " + this.name)
    console.log("seconds: " + this.secondsLearned)
    console.log("kp: " + this.kreditpunkte)
    console.log("semesterstufe: " + this.semesterstufe)
    console.log("semesterTyp: " + this.semesterTyp)
    console.log("semesterJahr: " + this.semesterJahr)
    console.log("klausur: " + this.klausurDate)
    console.log("lernage:" + this.lerntage)
  }

  getSemesterJahr(semesterTyp: string | undefined): string {
    let date = new Date().getFullYear()
    switch (semesterTyp){
      case 'SOMMERSEMESTER': return  date.toString()

      case 'WINTERSEMESTER': return  date + ' / ' + (date+1)

      default: return ''
    }
  }

  protected readonly Date = Date;
}
