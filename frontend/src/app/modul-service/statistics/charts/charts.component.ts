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
import {NgIf} from '@angular/common';
import {MatProgressSpinner} from '@angular/material/progress-spinner';

Chart.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController);

@Component({
  selector: 'app-charts',
  imports: [
    NgIf,
    MatProgressSpinner
  ],
  templateUrl: './charts.component.html',
  standalone: true,
  styleUrl: './charts.component.scss'
})
export class ChartsComponent implements  OnInit {
  isLoading : boolean = true

  chartStats: { [date: string]: { modulName: string; secondsLearned: string }[] } = {};

  service = inject(StatisticApiService)

  ngOnInit(): void {
    this.service.getChartLastDays().subscribe( {
      next: (value) => {
        this.chartStats = value;
        this.isLoading = false
      },
      complete: () => {
        setTimeout(() => {
          this.initChartOverall(
            "Minuten gelernt",
            this.getOverallLabels(this.chartStats),
            this.getOverallDataMinutes(this.chartStats),
            "chart-total"
          );

          this.initChartEachModule(
            "time learned per modul",
            this.getEachLabels(this.chartStats),
            this.getDatasets(this.chartStats),
            "chart-each"
          );
        }, 1);
      }
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
            backgroundColor: this.generateRgba(),
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

  initChartEachModule(label : string, labels : any, datasets : any, elementId : any) {
    const ctx = document.getElementById(elementId) as HTMLCanvasElement;
    return new Chart(ctx, {
      type: 'bar',
      data: {
        labels: labels,
        datasets: datasets
      },
      options: {
        responsive: true,
        scales: {
          x: {
            stacked: true,
          },
          y: {
            stacked: true
          }
        }
      }
    });
  }

  getOverallLabels(data : any) {
    return Object.keys(data).map(e => this.formatToGermanDate(e)).reverse();
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
    return timeLearned;
  }

  getEachLabels(data : any) {
    return Object.keys(data).map(e => this.formatToGermanDate(e)).reverse();
  }

  getDatasets(data: any) {
    const datasets :any[] = [];

    let colors : string[] = []

    for (let i = 0; i < 10; i++) colors.push(this.generateRgba())

    let colorIndex = 0;

    Object.keys(data).forEach(date => {
      data[date].forEach((entry : any) => {
        let dataset = datasets.find(ds => ds.label === entry.modulName);

        if (!dataset) {
          dataset = {
            label: entry.modulName,
            data: [],
            backgroundColor: colors[colorIndex % colors.length]
          };
          datasets.push(dataset);
        }
        colorIndex++;

        dataset.data.push(parseInt(entry.secondsLearned) / 60);
      });
    });

    return datasets;
  }

  formatToGermanDate(input: string): string {
    const date = new Date(input);

    const formatter = new Intl.DateTimeFormat('de-DE', {
      weekday: 'long',
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    });

    return formatter.format(date);
  }

  randomBlueColorVector() {
    let r = this.randomBetween(20, 100)
    let g = this.randomBetween(20, 100)
    let b = this.randomBetween(20, 255)
    return {r,g,b,a: 1}
  }

  randomBetween(min: number, max: number): number {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  generateRgba(): string {
    let color = this.randomBlueColorVector()
    const { r, g, b, a = 1 } = color;
    return `rgba(${r}, ${g}, ${b}, ${a})`;
  }
}
