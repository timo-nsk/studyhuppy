import {Component, inject, OnInit} from '@angular/core';
import {StatisticApiService} from '../statistic.service';
import {TimeFormatPipe} from '../../module/time-format.pipe';
import {NgFor} from '@angular/common';
import {LoggingService} from '../../../logging.service';

@Component({
  selector: 'app-general',
  imports: [TimeFormatPipe, NgFor],
  templateUrl: './general.component.html',
  standalone: true,
  styleUrl: './general.component.scss'
})
export class GeneralComponent implements OnInit{
  log = new LoggingService("GeneralComponent", "modul-service")
  service = inject(StatisticApiService)

  totalStudyTime: number = 0;
  totalStudyTimePerSemester: { key: number, value: number }[] = [];
  averageStudyTimePerDay : number = 0
  numberActiveModules: string = '';
  numberNotActiveModules: string = '';
  maxStudiedModul: string = '';
  minStudiedModul: string = '';

  ngOnInit(): void {
    this.service.getTotalStudyTime().subscribe({
      next: (data) => {
        this.totalStudyTime = data;
        this.log.info(`got total study time: ${data}`)
      },
      error: (err) => {
        this.log.error(`error getting total study time. reason: ${err}`)
      }
    });

    this.service.getTotalStudyTimeperSemester().subscribe({
      next: (data) => {
        this.totalStudyTimePerSemester = Object.entries(data).map(([key, value]) => ({
          key: Number(key),
          value: value
        }));
        this.log.info(`got total study time per semester: ${data}`)
      },
      error: (err) => {
        this.log.error(`error getting total study time per semester. reason: ${err}`)
      }
    });

    this.service.getDurchschnittlicheLernzeitProTag().subscribe({
      next: data => {
        this.averageStudyTimePerDay = data
        this.log.info(`got average lernzeit per day: ${data}`)
      },
      error: err => {
        this.log.info(`error getting average lernzeit per day. reason: ${err}`)
      }
    })

    this.service.getNumberActiveModules().subscribe({
      next: data => {
        this.log.info(`got no. of active module: ${data}`)
      },
      error: err => {
        this.log.error(`error getting no. of active module: ${err}`)
      }
    });

    this.service.getNumberNotActiveModules().subscribe({
      next: data => {
        this.log.info(`got no. of not active module: ${data}`)
      },
      error: err => {
        this.log.error(`error getting no. of not active module: ${err}`)
      }
    });

    this.service.getMaxStudiedModul().subscribe({
      next: data => {
        this.log.info(`got max studied modul: ${data}`)
      },
      error: err => {
        this.log.error(`error getting max studied modul: ${err}`)
      }
    });

    this.service.getMinStudiedModul().subscribe({
      next: data => {
        this.log.info(`got min studied modul: ${data}`)
      },
      error: err => {
        this.log.error(`error getting min studied modul: ${err}`)
      }
    });
  }
}
