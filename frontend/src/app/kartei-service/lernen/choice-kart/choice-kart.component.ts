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
  @Input() lernzeitTimer!: LernzeitTimer | undefined
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

  updateKarteikarte(i: number) {
    const data: UpdateInfo = {
      stapelId: this.stapelData?.fachId!,
      karteId: this.stapelData?.karteikarten![this.currentKartenIndex!].fachId!,
      schwierigkeit: this.btnDataList?.[i].schwierigkeit,
      secondsNeeded: this.lernzeitTimer!.getLernzeitCurrentKarte
    }
    this.karteiService.updateKarte(data).subscribe()
    this.lernzeitTimer!.startCurrentKarteTimer()
    this.showBtnGroup = !this.showBtnGroup

    let m = this.n ?? 0
    // Is this the last karte?
    if(this.currentKartenIndex == m - 1) {
      // Yes, then clear the overall timer
      this.lernzeitTimer!.clearOverallTimer()
      this.lernzeitTimer!.clearCurrentTimer()
      //and navigate to the kartei overview
      this.router.navigate(['/kartei'])
      this.snackbar.open("Stapel lernen beendet", "schließen", {
        duration: 3500
      })
    } else {
      // No, then clear the current timer
      this.lernzeitTimer!.clearCurrentTimer()

      // and increment the current index
      this.currentKartenIndex! += 1
      // Emit the updated index to the parent component, parent will initialize itself again with the next karteikarte
      this.karteIndexChange.emit(this.currentKartenIndex);
      // The Parent initialized itself with the next karteikarte and gives the new index to this component with new ButtonData
      // this component needs to initialize itself again with the new data
      this.init()
    }
  }
}
