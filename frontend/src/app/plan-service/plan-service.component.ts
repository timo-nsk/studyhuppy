import {Component, inject, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {PlanApiService} from './plan-api.service';
import {LernplanRequest} from './plan-domain';

@Component({
  selector: 'app-plan-service',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './plan-service.component.html',
  standalone: true,
  styleUrls: ['./plan-service.component.scss', '../general.scss', '../button.scss']
})
export class PlanServiceComponent implements OnInit{
  planApiService = inject(PlanApiService)
  activeLernplan : LernplanRequest = {} as LernplanRequest;
  activePlanExists: boolean = false;

  ngOnInit(): void {
    this.planApiService.getActiveLernplan().subscribe({
      next: (response) => {
        this.activeLernplan = response;
        this.activePlanExists = true;
        console.log(this.activeLernplan)
      },
      error: (status) => {
        if (status == 404) {
          this.activePlanExists = false;
          console.error('No active lernplan found for user');
        }
      }
    });
  }
}
