import {Component, inject, OnInit} from '@angular/core';
import {StatisticApiService} from '../statistic.service';
import {
  Chart,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  BarController
} from 'chart.js';

Chart.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController);

@Component({
  selector: 'app-charts',
  imports: [],
  templateUrl: './charts.component.html',
  standalone: true,
  styleUrl: './charts.component.scss'
})
export class ChartsComponent implements  OnInit {

  chartStats: { [date: string]: { modulName: string; secondsLearned: string }[] } = {};

  service = inject(StatisticApiService)

  ngOnInit(): void {
    this.service.getChartLastDays().subscribe(value => {
      this.chartStats = value;
      console.log("chartStats", this.chartStats);

      this.initChartOverall(
        "Minuten gelernt",
        this.getOverallLabels(this.chartStats),
        this.getOverallDataMinutes(this.chartStats),
        "chart-total"
      );
    });
  }


  initChartOverall(label : string, labels : any, data : any, elementId : any) {
    const ctx = document.getElementById(elementId) as HTMLCanvasElement;
    return new Chart(ctx, {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [
          {
            label: label,
            backgroundColor: 'rgba(84, 19, 136, 1)',
            data: data,
            borderWidth: 1
          }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  getOverallLabels(data : any) {
    console.log("keys: " + Object.keys(data))
    return Object.keys(data);
  }

  getOverallDataMinutes(data : any) {
    let timeLearned = [];

    for (const key in data) {
      let seconds = 0;
      let entry = data[key];

      for (let j = 0; j < entry.length; j++) {
        seconds += parseInt(entry[j].secondsLearned, 10);
      }
      timeLearned.push(seconds / 60);
    }
    console.log("time leanred: " + timeLearned)
    return timeLearned;
  }


}
