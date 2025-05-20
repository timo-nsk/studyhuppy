import {Component, inject, Input, OnInit} from '@angular/core';
import {Antwort, ButtonData, FrageTyp, Stapel, UpdateInfo} from '../../domain';
import {MatCheckbox} from '@angular/material/checkbox';
import {MatList, MatListItem} from '@angular/material/list';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {AntwortManager} from './antwort-manager.service';
import {MatRadioButton, MatRadioGroup} from '@angular/material/radio';
import {LernzeitTimer} from '../lernzeit-timer.service';
import {KarteiApiService} from '../../kartei.api.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-choice-kart',
  imports: [
    MatCheckbox,
    MatList,
    MatListItem,
    NgForOf,
    NgIf,
    NgClass,
    MatRadioGroup,
    MatRadioButton
  ],
  templateUrl: './choice-kart.component.html',
  standalone: true,
  styleUrls: ['./choice-kart.component.scss','../lernen.component.scss', '../../../button.scss']
})
export class ChoiceKartComponent implements OnInit{
  protected readonly FrageTyp = FrageTyp;

  karteiService = inject(KarteiApiService)
  router = inject(Router)
  snackbar = inject(MatSnackBar)

  @Input() stapelData : Stapel | undefined
  @Input() frage : string | undefined
  @Input() frageTyp : FrageTyp | undefined
  @Input() expectedAntworten : Antwort[] | undefined // Antworten der Karteikarte
  @Input() hideAntwortBtn : boolean = false
  @Input() hideBtnGroup : boolean = false
  @Input() btnDataList : any[] | undefined
  actualCorrectAnswers: boolean[] = [];
  tempIndex : number = 0
  n: number | undefined

  private am! : AntwortManager;
  @Input() lernzeitTimer!: LernzeitTimer;

  ngOnInit(): void {
    this.frage = this.stapelData?.karteikarten?.[this.tempIndex]?.frage
    this.frageTyp = this.stapelData?.karteikarten?.[this.tempIndex]?.frageTyp
    this.expectedAntworten = this.stapelData?.karteikarten?.[this.tempIndex]?.antworten
    this.n  = this.stapelData?.karteikarten?.length

    console.log(this.expectedAntworten)
    console.log(this.frageTyp)
    this.am = new AntwortManager(this.stapelData?.karteikarten?.[this.tempIndex].antworten?.length, this.expectedAntworten)
  }

  setAntwort(i: number) {
    this.am.setAntwort(i)
  }

  revealAntwort() {
    this.actualCorrectAnswers = this.am.compareAntworten(this.frageTyp)
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
    this.expectedAntworten = this.stapelData?.karteikarten?.[i].antworten
  }
}
