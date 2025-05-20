import {Component, inject, Input, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {Stapel, UpdateInfo} from '../../domain';
import {KarteiApiService} from '../../kartei.api.service';
import {LernzeitTimer} from '../lernzeit-timer.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-normal-karte',
  imports: [
    NgIf,
    NgForOf
  ],
  templateUrl: './normal-karte.component.html',
  standalone: true,
  styleUrls: ['./normal-karte.component.scss', '../lernen.component.scss', '../../../button.scss']
})
export class NormalKarteComponent implements OnInit{

  karteiService = inject(KarteiApiService)
  router = inject(Router)
  snackbar = inject(MatSnackBar)

  @Input() stapelData: Stapel | undefined
  @Input() btnDataList : any[] | undefined
  @Input() hideAntwort : boolean = true
  @Input() hideAntwortBtn : boolean = true
  @Input() hideBtnGroup : boolean = false
  tempIndex : number = 0

  frage: string | undefined;
  antwort: string | undefined;
  n: number | undefined
  @Input() currentKarteSeconds!: number;
  @Input() lernzeitTimer!: LernzeitTimer;

  ngOnInit(): void {
    this.frage = this.stapelData?.karteikarten?.[this.tempIndex].frage
    this.antwort = this.stapelData?.karteikarten?.[this.tempIndex].antwort
    this.n  = this.stapelData?.karteikarten?.length
  }

  revealAntwort() {
    this.hideAntwort = !this.hideAntwort
    this.hideAntwortBtn = false
    this.hideBtnGroup = true
  }

  updateKarteikarte(i: number, $event: MouseEvent) {
    const data: UpdateInfo = {
      stapelId: this.stapelData?.fachId!,
      karteId: this.stapelData?.karteikarten![this.tempIndex].fachId!,
      schwierigkeit: this.btnDataList?.[i].schwierigkeit,
      secondsNeeded: this.lernzeitTimer.getLernzeitCurrentKarte
    }
    this.karteiService.updateKarte(data).subscribe()
    this.lernzeitTimer.startCurrentKarteTimer(this.tempIndex, this.n, $event)

    let m = this.n ?? 0
    if(this.tempIndex == m - 1) {
      this.tempIndex = 0
      this.router.navigate(['/kartei'])
      this.snackbar.open("Stapel lernen beendet", "schließen", {
        duration: 3500
      })
    } else {
      this.tempIndex++
      this.nextKarte(this.tempIndex)
    }
  }

  nextKarte(i : number) {
    this.frage = this.stapelData?.karteikarten?.[i].frage
    this.antwort = this.stapelData?.karteikarten?.[i].antwort
  }
}
