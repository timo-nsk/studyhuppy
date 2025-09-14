import {Component, inject, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {SnackbarService} from '../snackbar.service';
import {PlanApiService} from './plan-api.service';
import {Lernplan} from './plan-domain';
import {ModuleApiService} from '../modul-service/module/module-api.service';


@Component({
  selector: 'app-plan-service',
  imports: [
    NgIf,
    RouterLink,
    NgForOf
  ],
  templateUrl: './plan-service.component.html',
  standalone: true,
  styleUrls: ['./plan-service.component.scss', '../general.scss', '../button.scss']
})
export class PlanServiceComponent implements OnInit {
  snackbarService = inject(SnackbarService)
  planApiService = inject(PlanApiService)
  modulService = inject(ModuleApiService)
  titel = "blub"
  lernplaene : Lernplan[] = [];
  hasModule = false

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
    this.checkHasModule()
  }

  deleteLernplan(fachId : string) {
    // delete lernplan by id
    this.planApiService.deleteLernplan(fachId).subscribe({
      next: (response) => {
        console.log(response)
        this.ngOnInit()
        this.snackbarService.openSuccess(`Lernplan "${this.titel}" gelöscht`)
      },
      error: (error) => {
        console.error('Error deleting lernplan:', error);
        this.snackbarService.openError('Fehler beim Löschen des Lernplans');
      }
    })
  }

  setActiveLernplan(fachId: string) {
    this.planApiService.setActiveLernplan(fachId).subscribe({
      next: (response) => {
        console.log(response)
        let activatedPlan = this.lernplaene.find(plan => plan.fachId === fachId)?.titel;
        this.snackbarService.openSuccess(`Lernplan "${activatedPlan}" aktiviert`)
        this.ngOnInit()
      },
      error: (error) => {
        console.error('Error setting active lernplan:', error);
        this.snackbarService.openError('Fehler beim Aktivieren des Lernplans');
      }
    })
  }

  checkHasModule() {
    this.modulService.hasModule().subscribe({
      next: res => {
        this.hasModule = res
      }
    })
  }

  hasLernplan() {
    return this.lernplaene.length > 0
  }
}
