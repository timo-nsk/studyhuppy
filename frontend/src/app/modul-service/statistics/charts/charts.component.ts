import {Component, inject, OnInit} from '@angular/core';
import {StatisticApiService} from '../statistic.service';
import { Chart,  CategoryScale,  LinearScale,  BarElement, Title, Tooltip, Legend, BarController} from 'chart.js';
import {NgIf} from '@angular/common';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {LoggingService} from '../../../logging.service';

Chart.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController);

type Dataset = {
  label: string;
  data: number[];
  backgroundColor: string;
};

@Component({
  selector: 'app-charts',
  imports: [NgIf, MatProgressSpinner],
  templateUrl: './charts.component.html',
  standalone: true,
  styleUrl: './charts.component.scss'
})
export class ChartsComponent implements  OnInit {
  log = new LoggingService("ChartsComponent", "modul")

  isLoading : boolean = true

  chartStats: { [date: string]: { modulName: string; secondsLearned: string }[] } = {};

  service = inject(StatisticApiService)

  printChartData(data : any) {
    this.log.debug("Got data...")
    for (const date in this.chartStats) {
      this.log.debug(`- Date: ${date}`)
      for (const entry of this.chartStats[date]) {
        this.log.debug(`- Modul: ${entry.modulName}, Sekunden: ${entry.secondsLearned}`)
      }
    }
  }

  ngOnInit(): void {
    this.service.getChartLastDays().subscribe( {
      next: (value) => {
        this.chartStats = value;

        this.printChartData(this.chartStats)

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
    return Object.keys(data).map(e => this.formatToGermanDate(e))
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
    return Object.keys(data).map(e => this.formatToGermanDate(e))
  }

  getModulNamen(data: { [date: string]: { modulName: string; secondsLearned: string }[] }) {
    const modulNamenSet = new Set<string>();
    Object.values(data).forEach(entries => {
      entries.forEach((entry: { modulName: string; secondsLearned: string }) => {
        modulNamenSet.add(entry.modulName);
      });
    });
    return Array.from(modulNamenSet);
  }

  prepareDataSets(modulNamen : string[], colors : string[]) : Dataset[] {
    return modulNamen.map((modulName, index) => ({
      label: modulName,
      data: [],
      backgroundColor: colors[index % colors.length]
    }));
  }

  getDatasets(data: { [date: string]: { modulName: string; secondsLearned: string }[] }) {
    this.log.debug("Process data...")
    let colors : string[] = []
    for (let i = 0; i < 10; i++) colors.push(this.generateRgba())

    const modulNamen = this.getModulNamen(data)

    const datasets : Dataset[] = this.prepareDataSets(modulNamen, colors)

    // Für jedes Datum für die secondsLearned des Moduls ein.
    // Wenn keine Statistik für das Modul an dem Datum vorhanden ist, setze 0 (Array-Längen müssen gleich sein)
    Object.keys(data).sort().forEach(date => {

      modulNamen.forEach(modulName => {
        const entry = data[date].find((e: any) => e.modulName === modulName);
        const dataset  = datasets.find(ds => ds.label === modulName);

        if (entry) {
          dataset!.data.push(parseFloat(String(parseInt(entry.secondsLearned) / 60)));
        } else {
          dataset!.data.push(0);
        }
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
