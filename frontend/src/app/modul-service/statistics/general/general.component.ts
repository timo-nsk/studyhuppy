import {Component, OnInit} from '@angular/core';
import {StatisticApiService} from '../statistic.service';
import {TimeFormatPipe} from '../../module/time-format.pipe';
import {NgFor} from '@angular/common';

@Component({
  selector: 'app-general',
  imports: [TimeFormatPipe, NgFor],
  templateUrl: './general.component.html',
  standalone: true,
  styleUrl: './general.component.scss'
})
export class GeneralComponent implements OnInit{

  totalStudyTime: number = 0;
  totalStudyTimePerSemester: { key: number, value: number }[] = [];
  averageStudyTimePerDay : number = 0
  numberActiveModules: string = '';
  numberNotActiveModules: string = '';
  maxStudiedModul: string = '';
  minStudiedModul: string = '';

  constructor(private service: StatisticApiService, private timeFormat : TimeFormatPipe) {}

  ngOnInit(): void {
    this.service.getTotalStudyTime().subscribe(value => {
      this.totalStudyTime = value;
    });

    this.service.getTotalStudyTimeperSemester().subscribe(data => {
      this.totalStudyTimePerSemester = Object.entries(data).map(([key, value]) => ({
        key: Number(key),
        value: value
      }));
    });

    this.service.getDurchschnittlicheLernzeitProTag().subscribe(data => {
      this.averageStudyTimePerDay = data
    })

    this.service.getNumberActiveModules().subscribe(value => {
      this.numberActiveModules = value;
    });

    this.service.getNumberNotActiveModules().subscribe(value => {
      this.numberNotActiveModules = value;
    });

    this.service.getMaxStudiedModul().subscribe(value => {
      this.maxStudiedModul = value;
    });

    this.service.getMinStudiedModul().subscribe(value => {
      this.minStudiedModul = value;
    });
  }
}
