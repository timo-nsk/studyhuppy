import {Component, inject, OnInit} from '@angular/core';
import {StatisticApiService} from '../statistic.service';
import {TimeFormatPipe} from '../../module/time-format.pipe';
import {KeyValuePipe, NgFor, NgIf} from '@angular/common';
import {LoggingService} from '../../../logging.service';
import {GeneralStatistics} from './general-statistics';

@Component({
  selector: 'app-general',
  imports: [TimeFormatPipe, NgFor, KeyValuePipe, NgIf],
  templateUrl: './general.component.html',
  standalone: true,
  styleUrl: './general.component.scss'
})
export class GeneralComponent implements OnInit{
  log = new LoggingService("GeneralComponent", "modul-service")
  statisticService = inject(StatisticApiService)

  generalStats : GeneralStatistics = {} as GeneralStatistics;

  ngOnInit(): void {
    this.getGeneralStats()
  }

  getGeneralStats() {
    this.statisticService.getGeneralStats().subscribe({
      next: data => {
        this.generalStats = data
        console.log(this.generalStats)
      },
      error: err => {
        console.log("something went wrong with fecthing general statistics dto")
      }
    })
  }

  isEmptyTotalStudyTimePerSemester(): boolean {
    return Object.keys(this.generalStats.totalStudyTimePerSemester).length === 0;
  }
}
