import {Component, OnInit} from '@angular/core';
import {StatisticApiService} from '../statistic.service';

@Component({
  selector: 'app-general',
  imports: [],
  templateUrl: './general.component.html',
  standalone: true,
  styleUrl: './general.component.scss'
})
export class GeneralComponent implements OnInit{

  totalStudyTime: number = 0;
  numberActiveModules: number = 0;
  numberNotActiveModules: number = 0;
  maxStudiedModul: string = '';
  minStudiedModul: string = '';

  constructor(private service: StatisticApiService) {}

  ngOnInit(): void {
    this.service.getTotalStudyTime().subscribe(value => {
      this.totalStudyTime = value;
    });

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
