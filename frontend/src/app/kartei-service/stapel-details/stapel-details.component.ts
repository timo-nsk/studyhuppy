import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Karteikarte, Stapel} from '../domain';
import {KarteiApiService} from '../kartei.api.service';
import {NgIf} from '@angular/common';
import {KarteErstellenComponent} from '../karte-erstellen/karte-erstellen.component';
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

@Component({
  selector: 'app-stapel-details',
  imports: [NgIf, KarteErstellenComponent, MatTable, MatHeaderCell, MatColumnDef, MatCell, MatCellDef, MatHeaderRow, MatRow, MatRowDef, MatHeaderCellDef, MatHeaderRowDef, MatAnchor],
  templateUrl: './stapel-details.component.html',
  standalone: true,
  styleUrl: './stapel-details.component.scss',
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

  showForm = false

  stapel : Stapel = {}
  karteikarten = new MatTableDataSource<Karteikarte>();
  displayedColumns: string[] = ['idx','frage', 'typ', 'option'];
  stapelId: string | null = ''

  route = inject(ActivatedRoute)
  karteiService = inject(KarteiApiService)

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => { this.stapelId = params.get('fachId'); });

    this.karteiService.getStapelByFachId(this.stapelId).subscribe({
      next: (data : Stapel) => {
        this.stapel = data
        this.karteikarten.data = data.karteikarten ?? []
      }
    })
  }


  showKarteErstellenForm() {
    this.showForm = !this.showForm
  }

  deleteKarte(stapelId: string | null, karteId: string) {
    //TODO implement
  }
}
