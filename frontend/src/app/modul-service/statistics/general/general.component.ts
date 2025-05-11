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

  totalStudyTime: string = '';
  totalStudyTimePerSemester: { key: number, value: number }[] = [];
  numberActiveModules: number = 0;
  numberNotActiveModules: number = 0;
  maxStudiedModul: string = '';
  minStudiedModul: string = '';

  constructor(private service: StatisticApiService) {}

  ngOnInit(): void {
    this.service.getTotalStudyTime().subscribe(value => {
      this.totalStudyTime = value;
      console.log(this.totalStudyTime)
    });

    this.service.getTotalStudyTimeperSemester().subscribe(data => {
      this.totalStudyTimePerSemester = Object.entries(data).map(([key, value]) => ({
        key: Number(key),
        value: value
      }));
    });

    this.service.getNumberActiveModules().subscribe(value => {
      this.numberActiveModules = value;
      console.log(this.numberActiveModules)
    });

    this.service.getNumberNotActiveModules().subscribe(value => {
      this.numberNotActiveModules = value;
      console.log(this.numberNotActiveModules)
    });

    this.service.getMaxStudiedModul().subscribe(value => {
      this.maxStudiedModul = value;
      console.log((this.maxStudiedModul))
    });

    this.service.getMinStudiedModul().subscribe(value => {
      this.minStudiedModul = value;
      console.log(this.minStudiedModul)
    });
  }



}
