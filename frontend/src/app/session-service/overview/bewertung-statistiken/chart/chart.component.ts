import {Component, inject, Input, OnChanges, SimpleChanges} from '@angular/core';
import {MonthlySessionBewertungMap, SessionApiService} from '../../../session-api.service';
import { Chart, LineController, LineElement, PointElement, LinearScale, Title, CategoryScale } from 'chart.js';

Chart.register(LineController, LineElement, PointElement, LinearScale, CategoryScale, Title);

@Component({
  selector: 'app-line-chart',
  imports: [],
  templateUrl: './chart.component.html',
  styleUrl: './chart.component.scss'
})
export class LineChartComponent implements OnChanges{
  @Input() selectedSessionId!: string;
  sessionBewertungStatistik!: MonthlySessionBewertungMap;
  sessionApiService = inject(SessionApiService);
  private chart: Chart | undefined;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedSessionId'] && this.selectedSessionId) {
      this.loadSession();
    }
  }

  initChart(labels: string[], datasets: any[], elementId: string) {
    const ctx = document.getElementById(elementId) as HTMLCanvasElement;

    if (this.chart) this.chart.destroy();

    this.chart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: labels,
        datasets: datasets
      },
      options: {
        responsive: true,
        plugins: {
          legend: { position: 'top' }
        },
        scales: {
          y: { beginAtZero: true }
        }
      }
    });
  }


  prepareChartData(response: MonthlySessionBewertungMap) {
    const labels = Object.keys(response).sort();

    const datasets = [
      {
        label: 'Konzentration',
        data: labels.map(date => response[date].averageKonzentrationBewertung),
        borderColor: 'rgb(75, 192, 192)',
        fill: false,
        tension: 0.1
      },
      {
        label: 'Produktivität',
        data: labels.map(date => response[date].averageProduktivitaetBewertung),
        borderColor: 'rgb(255, 99, 132)',
        fill: false,
        tension: 0.1
      },
      {
        label: 'Schwierigkeit',
        data: labels.map(date => response[date].averageSchwierigkeitBewertung),
        borderColor: 'rgb(255, 206, 86)',
        fill: false,
        tension: 0.1
      }
    ];

    return {
      labels,
      datasets
    };
  }

  private loadSession(): void {
    this.sessionApiService.getSessionBewertungStatistik(this.selectedSessionId).subscribe({
      next: data => {
        this.sessionBewertungStatistik = data;
        const chartData = this.prepareChartData(this.sessionBewertungStatistik);
        this.initChart(chartData.labels, chartData.datasets, 'chart');
      }
    });
  }
}
