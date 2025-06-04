import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {Stapel, UpdateInfo} from '../../domain';
import {KarteiApiService} from '../../kartei.api.service';
import {LernzeitTimer} from '../lernzeit-timer.service';
import {Router} from '@angular/router';
import {SnackbarService} from '../../../snackbar.service';
import {LoggingService} from '../../../logging.service';

@Component({
  selector: 'app-normal-karte',
  imports: [NgIf, NgForOf],
  templateUrl: './normal-karte.component.html',
  standalone: true,
  styleUrls: ['./normal-karte.component.scss', '../lernen.component.scss', '../../../button.scss']
})
export class NormalKarteComponent implements OnInit{
  log = new LoggingService("NormalKarteComponent", "kartei-service")

  karteiService = inject(KarteiApiService)
  router = inject(Router)
  sb = inject(SnackbarService)

  @Input() stapelData: Stapel | undefined
  @Input() btnDataList : any[] | undefined
  @Input() currentKartenIndex : number | undefined
  @Input() lernzeitTimer!: LernzeitTimer | undefined

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
  }

  revealAntwort() {
    this.showAntwort = !this.showAntwort
    this.showAntwortBtn = false
    this.showBtnGroup = true
  }

  updateKarteikarte(i: number) {
    //Prepare everything to update the current Karteikarte
    const data: UpdateInfo = {
      stapelId: this.stapelData?.fachId!,
      karteId: this.stapelData?.karteikarten![this.currentKartenIndex!].fachId!,
      schwierigkeit: this.btnDataList?.[i].schwierigkeit,
      secondsNeeded: this.lernzeitTimer!.getLernzeitCurrentKarte
    }

    this.karteiService.updateKarte(data).subscribe({
      next: () => {
        this.log.debug("Karteikarte updated successfully")
      },
      error: err => {
        this.log.error(`error updating Karteikarte. reason: ${err}`)
      }
    })

    this.showBtnGroup = !this.showBtnGroup

    let m = this.n ?? 0
    // Is this the last karte?
    if(this.currentKartenIndex == m - 1) {
      // Yes, then clear the overall timer
      this.lernzeitTimer!.clearOverallTimer()
      this.lernzeitTimer!.clearCurrentTimer()
      //and navigate to the kartei overview
      this.router.navigate(['/kartei'])
      this.sb.openInfo("Stapel lernen beendet")
      this.log.debug("Stapel lernen beendet, navigated to /kartei")
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
      this.log.debug(`Next karteikarte at index=${this.currentKartenIndex}`)
    }
  }
}
