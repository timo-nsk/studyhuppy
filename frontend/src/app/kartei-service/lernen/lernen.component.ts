import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {KarteiApiService} from '../kartei.api.service';
import {FrageTyp, Stapel, UpdateInfo} from '../domain';
import {NgIf} from '@angular/common';
import {ButtonDataGenerator} from '../button.data.generator';
import {MatSnackBar} from '@angular/material/snack-bar';
import {TimeFormatPipe} from '../../modul-service/module/time-format.pipe';
import {NormalKarteComponent} from './normal-karte/normal-karte.component';
import {ChoiceKartComponent} from './choice-kart/choice-kart.component';
import {LernzeitTimer} from './lernzeit-timer.service';


@Component({
  selector: 'app-lernen',
  imports: [NgIf, TimeFormatPipe, NormalKarteComponent, ChoiceKartComponent],
  templateUrl: './lernen.component.html',
  standalone: true,
  styleUrls: ['./lernen.component.scss', '../../button.scss']
})
export class LernenComponent implements OnInit {
  protected readonly FrageTyp = FrageTyp;

  startedOverallTimer: boolean = false
  lernzeitTimer : LernzeitTimer | undefined

  stapel : Stapel = {};
  stapelId : string | undefined = ''

  kartenIndex = 0
  btnDataList : any[] = []

  route = inject(ActivatedRoute)
  router = inject(Router)
  karteiService = inject(KarteiApiService)
  snackbar = inject(MatSnackBar)
  gen!: ButtonDataGenerator

  ngOnInit(): void {
    let stapelId: string | null = this.getStapelIdFromRoute()
    this.initLearnSession(stapelId)
  }

  getStapelIdFromRoute(): string | null {
    let id: string | null = '';
    this.route.paramMap.subscribe(params => { id = params.get('fachId'); });
    return id;
  }

  initLearnSession(id: string | null | undefined) {
    this.karteiService.getStapelByFachId(id).subscribe({
      next: (data : Stapel) => {
        this.stapel = data
        console.log(this.stapel.karteikarten)
        this.stapelId = data.fachId
        let n = this.stapel.karteikarten?.length ?? 0
        if(!this.startedOverallTimer) {
          this.lernzeitTimer = new LernzeitTimer(n)
          this.startedOverallTimer = true
        }

        // Generate button data for the current card
        this.gen = new ButtonDataGenerator(data.karteikarten?.[this.kartenIndex]);
        this.btnDataList = this.gen.generateButtons()

        this.lernzeitTimer!.startCurrentKarteTimer()
      }
    })
  }

  updateKartenIndexFromChild(updatedIndex: number) {
    let before = this.kartenIndex
    this.kartenIndex = updatedIndex;

    this.initLearnSession(this.stapelId)
    console.log("--- updated karten index from child: " + this.kartenIndex + " before: " + before)
  }

  frageTyp(karteIndex: number): FrageTyp | undefined {
    return this.stapel.karteikarten?.[karteIndex]?.frageTyp
  }
}
