import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
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
  @Input() currentKartenIndex : number | undefined
  @Input() lernzeitTimer!: LernzeitTimer

  @Output() karteIndexChange = new EventEmitter<number>();

  showAntwort : boolean = false
  showAntwortBtn : boolean = true
  showBtnGroup : boolean = false

  frage: string | undefined;
  antwort: string | undefined;
  n: number | undefined


  ngOnInit(): void {
    this.init()
  }

  init() {
    this.frage = this.stapelData?.karteikarten?.[this.currentKartenIndex!].frage
    this.antwort = this.stapelData?.karteikarten?.[this.currentKartenIndex!].antwort
    this.n  = this.stapelData?.karteikarten?.length
    this.showAntwort = false
    this.showAntwortBtn = true
    this.showBtnGroup = false
    console.log(this.btnDataList)
  }

  revealAntwort() {
    this.showAntwort = !this.showAntwort
    this.showAntwortBtn = !this.showAntwortBtn
    this.showBtnGroup = !this.showBtnGroup
  }

  updateKarteikarte(i: number, $event: MouseEvent) {
    const data: UpdateInfo = {
      stapelId: this.stapelData?.fachId!,
      karteId: this.stapelData?.karteikarten![this.currentKartenIndex!].fachId!,
      schwierigkeit: this.btnDataList?.[i].schwierigkeit,
      secondsNeeded: this.lernzeitTimer.getLernzeitCurrentKarte
    }
    this.karteiService.updateKarte(data).subscribe()
    this.lernzeitTimer.startCurrentKarteTimer(this.currentKartenIndex!, this.n, $event)
    this.showBtnGroup = !this.showBtnGroup

    let m = this.n ?? 0
    if(this.currentKartenIndex == m - 1) {
      this.router.navigate(['/kartei'])
      this.snackbar.open("Stapel lernen beendet", "schließen", {
        duration: 3500
      })
    } else {
      this.currentKartenIndex! += 1
      this.karteIndexChange.emit(this.currentKartenIndex);
      this.init()
    }
  }
}
