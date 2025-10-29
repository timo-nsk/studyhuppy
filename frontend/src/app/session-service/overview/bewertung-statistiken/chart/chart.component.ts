import {Component, inject, Input, OnChanges, SimpleChanges} from '@angular/core';
import {MonthlySessionBewertungMap, SessionApiService} from '../../../session-api.service';

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

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedSessionId'] && this.selectedSessionId) {
      this.loadSession();
    }
  }

  private loadSession(): void {
    this.sessionApiService.getSessionBewertungStatistik(this.selectedSessionId).subscribe({
      next: data => {
        this.sessionBewertungStatistik = data;
        console.log("chart sessionBewertungStatistik: ", this.sessionBewertungStatistik);
      }
    });
  }
}
