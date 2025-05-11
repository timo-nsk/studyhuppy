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

  totalStudyTime: string = '';
  numberActiveModules: number = 0;
  numberNotActiveModules: number = 0;
  maxStudiedModul: string = '';
  minStudiedModul: string = '';

  constructor(private service: StatisticApiService) {}

  ngOnInit(): void {
    this.service.getTotalStudyTime().subscribe(value => {
      console.log
      this.totalStudyTime = value;
      console.log(this.totalStudyTime)
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
