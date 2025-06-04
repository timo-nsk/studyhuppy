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
import {LoggingService} from '../../logging.service';
import {SnackbarService} from '../../snackbar.service';


@Component({
  selector: 'app-lernen',
  imports: [NgIf, TimeFormatPipe, NormalKarteComponent, ChoiceKartComponent],
  templateUrl: './lernen.component.html',
  standalone: true,
  styleUrls: ['./lernen.component.scss', '../../button.scss']
})
export class LernenComponent implements OnInit {
  protected readonly FrageTyp = FrageTyp;

  log = new LoggingService("LernenComponent", "kartei-service")

  route = inject(ActivatedRoute)
  router = inject(Router)
  karteiService = inject(KarteiApiService)
  sb = inject(SnackbarService)

  startedOverallTimer: boolean = false
  lernzeitTimer : LernzeitTimer | undefined

  stapel : Stapel = {};
  stapelId : string | undefined = ''

  kartenIndex = 0
  btnDataList : any[] = []
  gen!: ButtonDataGenerator

  ngOnInit(): void {
    let stapelId: string | null = this.getStapelIdFromRoute()
    this.initLearnSession(stapelId)
  }

  getStapelIdFromRoute(): string | null {
    let id: string | null = ''
    this.route.paramMap.subscribe({
      next: params => {
        id = params.get('fachId')
        this.log.debug(`Retrieved stapelId from route parameters: ${id}`)
        return id
      },
      error: err => {
        this.log.error(`Error retrieving stapelId from route parameters. reason: ${err}`)
        this.sb.openError('Fehler beim Laden des Stapels')
        return id
      }
    })
    return id
  }

  initLearnSession(id: string | null | undefined) {
    this.karteiService.getStapelByFachId(id).subscribe({
      next: (data : Stapel) => {
        this.log.info(`got data for stapel: ${data}`)
        this.stapel = data
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
    this.kartenIndex = updatedIndex

    this.initLearnSession(this.stapelId)
    this.log.debug(`--- updated karten index from child. before ${before}, after: ${this.kartenIndex}`)
  }

  frageTyp(karteIndex: number): FrageTyp | undefined {
    return this.stapel.karteikarten?.[karteIndex]?.frageTyp
  }
}
