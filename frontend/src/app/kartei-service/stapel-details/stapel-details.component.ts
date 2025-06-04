import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FrageTyp, Karteikarte, Stapel} from '../domain';
import {KarteiApiService} from '../kartei.api.service';
import {DatePipe, NgClass, NgIf} from '@angular/common';
import { trigger, transition, style, animate } from '@angular/animations';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {MatAnchor} from '@angular/material/button';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FragetypFormatPipe} from '../fragetyp-format.pipe';
import {KarteBearbeitenComponent} from './karte-bearbeiten/karte-bearbeiten.component';
import {KarteErstellenComponent} from './karte-erstellen/karte-erstellen.component';
import {KarteImportComponent} from './karte-import/karte-import.component';
import {SnackbarService} from '../../snackbar.service';
import {LoggingService} from '../../logging.service';

@Component({
  selector: 'app-stapel-details',
  imports: [NgIf, KarteBearbeitenComponent, KarteErstellenComponent, KarteImportComponent, MatTable, MatHeaderCell, MatColumnDef, MatCell, MatCellDef, MatHeaderRow, MatRow, MatRowDef, MatHeaderCellDef, MatHeaderRowDef, MatAnchor, FragetypFormatPipe, DatePipe, NgClass],
  templateUrl: './stapel-details.component.html',
  standalone: true,
  styleUrls: ['./stapel-details.component.scss', '../../general.scss'],
  animations: [
    trigger('slideDown', [
      transition(':enter', [
        style({ transform: 'scaleY(0)', transformOrigin: 'top', opacity: 0 }),
        animate('300ms ease-out', style({ transform: 'scaleY(1)', opacity: 1 }))
      ]),
      transition(':leave', [
        animate('200ms ease-in', style({ transform: 'scaleY(0)', opacity: 0 }))
      ])
    ])
  ]
})
export class StapelDetailsComponent implements OnInit{
  log = new LoggingService("StapelDetailsComponent", "kartei-service")

  showErstellenForm = false
  showBearbeitenForm = false
  showImportForm = false

  stapel : Stapel = {}
  karteikarten = new MatTableDataSource<Karteikarte>();
  karteToEdit : Karteikarte = {}
  displayedColumns: string[] = ['idx','frage', 'antwort', 'typ', 'due', 'option'];
  stapelId: string | null = ''

  route = inject(ActivatedRoute)
  router = inject(Router)
  karteiService = inject(KarteiApiService)
  sb = inject(SnackbarService)

  ngOnInit(): void {
    this.getRouteParams()
    this.loadStapel()
  }

  getRouteParams() {
    this.route.paramMap.subscribe({
      next: params => {
        this.stapelId = params.get('fachId');
        this.log.debug("Successfully retrieved route params")
      },
      error: err => {
        this.log.error(`Error retrieving route params. Reason: ${err}`)
      }
    })
  }

  loadStapel() : void {
    this.karteiService.getStapelWithAllKarteikartenByFachId(this.stapelId).subscribe({
      next: (data : Stapel) => {
        this.stapel = data
        this.karteikarten.data = data.karteikarten ?? []
        this.log.debug(`got stapel data: ${data}`)
      },
      error: err => {
        this.log.error(`error getting stapel data. reason: ${err}`)
      }
    })
  }

  deleteKarte(stapelId: string | null, karteId: string) {
    this.karteiService.deleteKarte(stapelId, karteId).subscribe({
      next: () => {
        this.log.debug(`deleted karteikarte from stapel ${stapelId}`)
        this.sb.openInfo("karteikarte erfolgreich gelöscht")
        this.loadStapel()
      },
      error: err => {
        this.log.error(`error deleting karteikarte from stapel. reason: ${err}`)
        this.sb.openError("karteikarte konnte nicht gelöscht werden")
      }
    })
  }

  showKarteErstellenForm() {
    this.showErstellenForm = !this.showErstellenForm
  }

  showImportierenForm() {
    this.showImportForm = !this.showImportForm
  }

  showKarteBearbeitenForm(withKarte : Karteikarte) {
    this.showBearbeitenForm = !this.showBearbeitenForm
    this.karteToEdit = withKarte
  }

  isFaellig(faelligAm: string | Date): boolean {
    const date = new Date(faelligAm);
    return date.getTime() < Date.now();
  }

  protected readonly FrageTyp = FrageTyp;
}
