import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {KarteiApiService} from '../kartei.api.service';
import {Stapel, UpdateInfo} from '../domain';
import {NgIf, NgFor} from '@angular/common';
import {ButtonDataGenerator} from '../button.data.generator';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {TimeFormatPipe} from '../../modul-service/module/time-format.pipe';


@Component({
  selector: 'app-lernen',
  imports: [NgIf, NgFor, TimeFormatPipe],
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
  //TODO: aus dem backend muss der stapel so geändert werden, dass da nur die fälligen karten reingepackt werden
  thisStapel : Stapel = {};
  gen!: ButtonDataGenerator;

  ngOnInit(): void {
    let id: string | null = '';
    this.route.paramMap.subscribe(params => { id = params.get('fachId'); });

    this.karteiService.getStapelByFachId(id).subscribe({
      next: (data : Stapel) => {
        this.thisStapel = data
        //console.log(this.thisStapel)

        this.gen = new ButtonDataGenerator(data.karteikarten?.[this.kartenIndex]);
        this.btnDataList = this.gen.generateButtons()
        console.log(this.btnDataList)

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
        console.log("Timer gestoppt – letzte Karte erreicht");
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
      console.log("Sekunden für aktuelle Karte:", this.currentKarteSeconds);
    }, 1000);

    if (this.kartenIndex >= this.thisStapel.karteikarten!.length - 1) {
      clearInterval(this.currentTimerId);
      console.log("Timer gestoppt – letzte Karte erreicht");
    }
  }


}
