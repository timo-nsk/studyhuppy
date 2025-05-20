import {Component, Input, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {Stapel} from '../../domain';

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

  @Input() stapelData: Stapel | undefined
  @Input() btnDataList : any[] | undefined
  @Input() kartenIndex!: number
  @Input() hideAntwort : boolean = true
  @Input() hideAntwortBtn : boolean = true
  @Input() hideBtnGroup : boolean = false

  frage: string | undefined;
  antwort: string | undefined;

  ngOnInit(): void {
    this.frage = this.stapelData?.karteikarten?.[this.kartenIndex].frage
    this.antwort = this.stapelData?.karteikarten?.[this.kartenIndex].antwort
  }

  revealAntwort() {
    this.hideAntwort = !this.hideAntwort
    this.hideAntwortBtn = false
    this.hideBtnGroup = true
  }

  updateKarteikarte(i: number, $event: MouseEvent) {
    console.log("updating normal karteikarte")
  }
}
