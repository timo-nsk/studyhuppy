import {Component, inject, OnInit} from '@angular/core';
import {ActuatorApiService} from './actuator.api.service';
import {SystemHealth} from './system-health';
import {NgIf} from '@angular/common';
import {
  MatCell, MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef,
  MatRow, MatRowDef,
  MatTable
} from '@angular/material/table';
import {RouterLink, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-actuator-service',
  imports: [
    NgIf,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatHeaderCellDef,
    MatCellDef,
    MatHeaderRowDef,
    MatRowDef,
    RouterLink,
    RouterOutlet
  ],
  templateUrl: './actuator-service.component.html',
  standalone: true,
  styleUrls: ['./actuator-service.component.scss', '../../../color.scss', '../../../general.scss']
})
export class ActuatorServiceComponent implements OnInit{
  apiService = inject(ActuatorApiService)
  systemHealth : SystemHealth[] = [];
  displayedColumns: string[] = ['service', 'ready', 'live'];

  ngOnInit(): void {
    this.apiService.getSystemHealth().subscribe({
      next: (data : SystemHealth[]) => {
        this.systemHealth = data
        console.log("got system health")
        console.log(this.systemHealth)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }
}
