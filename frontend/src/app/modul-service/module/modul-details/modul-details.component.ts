import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TimeFormatPipe} from '../time-format.pipe';
import {DatePipe, NgIf} from '@angular/common';
import {CapitalizePipe} from '../capitalize.pipe';
import {LoggingService} from '../../../logging.service';

@Component({
  selector: 'app-modul-details',
  imports: [ NgIf, TimeFormatPipe, DatePipe, CapitalizePipe ],
  templateUrl: './modul-details.component.html',
  standalone: true,
  styleUrls: ['./modul-details.component.scss', '../../../general.scss', '../../../button.scss', '../../../color.scss']
})
export class ModulDetailsComponent implements  OnInit{
  log = new LoggingService("ModulDetailsComponent", "modul-service")

  name!: string
  secondsLearned!: number
  kreditpunkte!: number
  kontaktzeitStunden!: number
  selbststudiumStunden!: number
  semesterstufe!: number
  semesterTyp!: string
  semesterJahr: string = '2000'
  lerntage!: string[]

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
      this.lerntage = params['lerntage']

      this.queryParamLog()
    });
  }

  queryParamLog() {
    this.log.debug("name: " + this.name)
    this.log.debug("seconds: " + this.secondsLearned)
    this.log.debug("kp: " + this.kreditpunkte)
    this.log.debug("semesterstufe: " + this.semesterstufe)
    this.log.debug("semesterTyp: " + this.semesterTyp)
    this.log.debug("semesterJahr: " + this.semesterJahr)
    this.log.debug("lernage:" + this.lerntage)
  }

  getSemesterJahr(semesterTyp: string | undefined): string {
    let date = new Date().getFullYear()
    switch (semesterTyp){
      case 'SOMMERSEMESTER': return  date.toString()

      case 'WINTERSEMESTER': return  date + ' / ' + (date+1)

      default: return ''
    }
  }

  computeLerntageString() : string {
    let lernTageArr  = this.lerntage

    if (!lernTageArr) return "keine Lerntage eingetragen"

    const wochentage = [
      'montags',
      'dienstags',
      'mittwochs',
      'donnerstags',
      'freitags',
      'samstags',
      'sonntags'
    ];

    let lerntageString = ''
    for(let i = 0; i < lernTageArr.length; i++) {
      if (lernTageArr[i] === "true") {
        if (lerntageString) {
          lerntageString += ', ';
        }
        lerntageString += wochentage[i];
      }
    }

    return lerntageString
  }
}
