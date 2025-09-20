import {Component, inject, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {SessionApiService} from '../../session-service/session-api.service';
import {SessionInfoDto} from '../../session-service/session-domain';
import {PlanFormComponent} from '../plan-form/plan-form.component';

@Component({
  selector: 'app-create',
  imports: [
    ReactiveFormsModule,
    NgIf,
    FormsModule,
    RouterLink,
    PlanFormComponent
  ],
  templateUrl: './create.component.html',
  standalone: true,
  styleUrls: ['./create.component.scss', '../../general.scss', '../../button.scss', '../../color.scss']
})
export class PlanCreateComponent implements OnInit {
  sessionApiService = inject(SessionApiService)
  sessionData : SessionInfoDto[] = []
  loadingData = true

  ngOnInit(): void {
    this.sessionApiService.getLernplanSessionData().subscribe({
      next: (data) => {
        this.sessionData = data
        this.loadingData = false
      },
      error: (err) => {
        console.error('Error fetching session data:', err);
      }
    })
  }
}
