import {Component, inject, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {SnackbarService} from '../snackbar.service';
import {PlanApiService} from './plan-api.service';
import {Lernplan} from './plan-domain';


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
export class PlanServiceComponent implements OnInit {
  snackbarService = inject(SnackbarService)
  planApiService = inject(PlanApiService)
  titel = "blub"
  lernplaene : Lernplan[] = [];

  ngOnInit(): void {
    this.planApiService.getAllLernplaene().subscribe({
      next: (response) => {
        this.lernplaene = response
        console.log(this.lernplaene)
      },
      error: (error) => {
        console.error('Error fetching lernplaene:', error);
        this.snackbarService.openError('Fehler beim Laden der Lernpläne');
      }
    })
  }

  deleteLernplan() {
    // delete lernplan by id
    console.log("deleted")
    this.snackbarService.openSuccess(`Lernplan "${this.titel}" gelöscht`)
  }
}
