import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {KarteiApiService} from '../kartei.api.service';
import {Antwort, FrageTyp, Stapel, UpdateInfo} from '../domain';
import {NgClass, NgFor, NgIf} from '@angular/common';
import {ButtonDataGenerator} from '../button.data.generator';
import {MatSnackBar} from '@angular/material/snack-bar';
import {TimeFormatPipe} from '../../modul-service/module/time-format.pipe';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {MatList, MatListItem} from '@angular/material/list';
import {MatCheckbox} from '@angular/material/checkbox';


@Component({
  selector: 'app-lernen',
  imports: [NgIf, NgFor, TimeFormatPipe, MatCell, MatCellDef, MatColumnDef, MatHeaderCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable, MatHeaderCellDef, MatList, MatListItem, MatCheckbox, NgClass],
  templateUrl: './lernen.component.html',
  standalone: true,
  styleUrls: ['./lernen.component.scss', '../../button.scss']
})
export class LernenComponent implements OnInit {
  overallSeconds : number = 0
  currentKarteSeconds : number = 0
  currentTimerId: any;

  hideAntwort : boolean = true
  hideAntwortBtn : boolean = true
  hideBtnGroup : boolean = false
  btnDataList : any[] = []

  kartenIndex = 0

  route = inject(ActivatedRoute)
  router = inject(Router)
  karteiService = inject(KarteiApiService)
  snackbar = inject(MatSnackBar)
  antworten = new MatTableDataSource<Antwort>();
  displayedColumns: string[] = ['wahr','antwort'];
  //TODO: aus dem backend muss der stapel so geändert werden, dass da nur die fälligen karten reingepackt werden
  thisStapel : Stapel = {};
  gen!: ButtonDataGenerator;
  antwortenChoicesFormArray : boolean[] = []
  actualCorrectAnswers : boolean[] = []

  ngOnInit(): void {
    let id: string | null = '';
    this.route.paramMap.subscribe(params => { id = params.get('fachId'); });

    this.karteiService.getStapelByFachId(id).subscribe({
      next: (data : Stapel) => {
        this.thisStapel = data
        if (this.thisStapel.karteikarten) {
          this.antworten.data = this.thisStapel.karteikarten[this.kartenIndex].antworten ?? []
        }

        this.gen = new ButtonDataGenerator(data.karteikarten?.[this.kartenIndex]);
        this.initAntwortenChoicesFormArray(this.thisStapel)
        this.btnDataList = this.gen.generateButtons()

        this.startOverallTimer()
        this.startCurrentKarteTimer(null)
      }
    })

  }

  toggleAntwort() {
    this.hideAntwort = false
    this.hideAntwortBtn = false
    this.hideBtnGroup = true
  }

  toggleChoiceAntwort() {
    this.compareAntworten()
    console.log("result= " + this.actualCorrectAnswers)
    this.hideAntwort = false
    this.hideAntwortBtn = false
    this.hideBtnGroup = true
  }

  updateKarteikarte(i : number, event : MouseEvent) {
    const data: UpdateInfo = {
      stapelId: this.thisStapel.fachId!,
      karteId: this.thisStapel.karteikarten![this.kartenIndex].fachId!,
      schwierigkeit: this.btnDataList[i].schwierigkeit,
      secondsNeeded: this.currentKarteSeconds
    }
    this.karteiService.updateKarte(data)
    this.startCurrentKarteTimer(event)

    let n = this.thisStapel.karteikarten?.length!
    if(this.kartenIndex == n - 1) {
      this.kartenIndex = 0
      this.router.navigate(['/kartei'])
      this.snackbar.open("Stapel lernen beendet", "schließen", {
        duration: 3500
      })
    }else {
      this.kartenIndex++
    }
  }

  startOverallTimer(): void {
    const intervalId = setInterval(() => {
      this.overallSeconds++;

      if (this.kartenIndex >= this.thisStapel.karteikarten!.length - 1) {
        clearInterval(intervalId);
        //console.log("Timer gestoppt – letzte Karte erreicht");
      }
    }, 1000);
  }

  startCurrentKarteTimer(event: MouseEvent | null): void {
    if (event) {
      this.currentKarteSeconds = 0;

      if (this.currentTimerId) {
        clearInterval(this.currentTimerId);
      }
    }

    this.currentTimerId = setInterval(() => {
      this.currentKarteSeconds++;
      //console.log("Sekunden für aktuelle Karte:", this.currentKarteSeconds);
    }, 1000);

    if (this.kartenIndex >= this.thisStapel.karteikarten!.length - 1) {
      clearInterval(this.currentTimerId);
      //console.log("Timer gestoppt – letzte Karte erreicht");
    }
  }
  protected readonly FrageTyp = FrageTyp;

  initAntwortenChoicesFormArray(data : Stapel) {
    let karteikarte = data.karteikarten?.[this.kartenIndex]
    if(karteikarte?.frageTyp == FrageTyp.MULTIPLE_CHOICE || karteikarte?.frageTyp == FrageTyp.SINGLE_CHOICE) {
      let n = karteikarte.antworten?.length ?? 0
      for(let i = 0; i < n; i++) {
        this.antwortenChoicesFormArray[i] = false
      }
    }
  }

  setAntwort(i: number) {
    this.antwortenChoicesFormArray[i] = !this.antwortenChoicesFormArray[i]
    console.log(this.antwortenChoicesFormArray)
  }

  private compareAntworten() {
    let n = this.antwortenChoicesFormArray.length
    for(let i = 0; i < n; i++) {
      let actualActualCorrect = this.thisStapel.karteikarten?.[this.kartenIndex].antworten?.[i]?.wahrheit
      let userCorrect = this.antwortenChoicesFormArray[i]
      if(actualActualCorrect == userCorrect) {
        this.actualCorrectAnswers.push(true)
      } else {
        this.actualCorrectAnswers.push(false)
      }
    }
  }
}
