import {Component, inject, Input, OnInit} from '@angular/core';
import {Antwort, ButtonData, FrageTyp, Stapel} from '../../domain';
import {MatCheckbox} from '@angular/material/checkbox';
import {MatList, MatListItem} from '@angular/material/list';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {AntwortManager} from './antwort-manager.service';

@Component({
  selector: 'app-choice-kart',
  imports: [
    MatCheckbox,
    MatList,
    MatListItem,
    NgForOf,
    NgIf,
    NgClass
  ],
  templateUrl: './choice-kart.component.html',
  standalone: true,
  styleUrls: ['./choice-kart.component.scss','../lernen.component.scss']
})
export class ChoiceKartComponent implements OnInit{
  protected readonly FrageTyp = FrageTyp;

  @Input() stapelData : Stapel | undefined
  @Input() kartenIndex! : number;
  @Input() frage : string | undefined
  @Input() frageTyp : FrageTyp | undefined
  @Input() expectedAntworten : Antwort[] | undefined // Antworten der Karteikarte
  @Input() hideAntwortBtn : boolean = false
  @Input() hideBtnGroup : boolean = false
  @Input() btnDataList : any[] | undefined
  actualCorrectAnswers: boolean[] = [];

  private am! : AntwortManager;

  ngOnInit(): void {
    this.frage = this.stapelData?.karteikarten?.[this.kartenIndex]?.frage
    this.frageTyp = this.stapelData?.karteikarten?.[this.kartenIndex]?.frageTyp
    this.expectedAntworten = this.stapelData?.karteikarten?.[this.kartenIndex]?.antworten

    console.log(this.expectedAntworten)
    this.am = new AntwortManager(this.stapelData?.karteikarten?.[this.kartenIndex].antworten?.length, this.expectedAntworten)
  }

  setAntwort(i: number) {
    this.am.setAntwort(i)
  }

  revealAntwort() {
    this.actualCorrectAnswers = this.am.compareAntworten()
    this.hideAntwortBtn = false
    this.hideBtnGroup = true
  }

  updateKarteikarte(i: number, $event: MouseEvent) {
    // TODO: implement
    console.log("updating karteikarte")
  }
}
