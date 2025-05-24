import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {Antwort, ButtonData, FrageTyp, Stapel, UpdateInfo} from '../../domain';
import {MatCheckbox} from '@angular/material/checkbox';
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
  @Input() btnDataList : any[] | undefined
  @Input() lernzeitTimer!: LernzeitTimer
  @Input() currentKartenIndex : number | undefined

  @Output() karteIndexChange = new EventEmitter<number>();

  frage : string | undefined
  frageTyp : FrageTyp | undefined
  expectedAntworten : Antwort[] | undefined // Antworten der Karteikarte
  actualCorrectAnswers: boolean[] = []

  showAntwortBtn : boolean = false
  showBtnGroup : boolean = false

  n: number | undefined

  private am! : AntwortManager

  ngOnInit(): void {
    this.init()
  }

  init() {
    this.frage = this.stapelData?.karteikarten?.[this.currentKartenIndex!]?.frage
    this.frageTyp = this.stapelData?.karteikarten?.[this.currentKartenIndex!]?.frageTyp
    this.expectedAntworten = this.stapelData?.karteikarten?.[this.currentKartenIndex!]?.antworten
    this.n  = this.stapelData?.karteikarten?.length
    this.showAntwortBtn = true
    this.showBtnGroup = false
    this.am = new AntwortManager(this.stapelData?.karteikarten?.[this.currentKartenIndex!].antworten?.length, this.expectedAntworten)

  }

  setAntwort(i: number) {
    this.am.setAntwort(i)
  }

  revealAntwort() {
    this.actualCorrectAnswers = this.am.compareAntworten(this.frageTyp)
    this.showAntwortBtn = false
    this.showBtnGroup = true
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
      this.karteIndexChange.emit(this.currentKartenIndex)
      this.init()
    }
  }
}
