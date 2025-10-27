import {Component, effect, inject, OnInit} from '@angular/core';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {SessionStateManager} from '../session-state-manager.service';
import {Session, SessionBewertung} from '../session-domain';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {SessionApiService} from '../session-api.service';
import {TimeFormatPipe} from '../../modul-service/module/time-format.pipe';
import {ModuleApiService} from '../../modul-service/module/module-api.service';
import {SessionSignalService} from './session-signal.service';
import { Modal } from 'bootstrap';
import {RatingComponent, RatingModule} from 'ngx-bootstrap/rating';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-session-start',
  imports: [NgIf, RouterLink, NgForOf, TimeFormatPipe, NgClass, RatingComponent, FormsModule],
  templateUrl: './start.component.html',
  standalone: true,
  styleUrls: ['./start.component.scss', '../../general.scss', '../../button.scss']
})
export class SessionStartComponent implements OnInit{
  sessions: any
  selectedSession!: Session | null;
  sessionBewertung : SessionBewertung = {konzentrationBewertung: 0, produktivitaetBewertung: 0, schwierigkeitBewertung: 0}
  route = inject(ActivatedRoute)
  sessionApiService = inject(SessionApiService)
  modulService = inject(ModuleApiService)
  sessionStateManager!: SessionStateManager;
  sessionStarted = true;
  sessionPaused = false;
  sessionSelectIsDisabled = false
  sessionAbgebrochen = false

  constructor(private sessionSignalService: SessionSignalService) {
    effect(() => {
      if (this.sessionSignalService.sessionFinished() === -1) {
        this.resetButtons()
        this.openRatingModal()
        this.sessionSignalService.resetThisSession()
      }
    });
  }

  ngOnInit(): void {
    const sessionId = this.route.snapshot.paramMap.get('sessionId');

    if(sessionId == null) {
      this.sessionApiService.getSessions().subscribe({
        next: (data) => {
          if (!data) {
            console.log("Keine Sessions vorhanden (204 No Content)");
            this.sessions = null;
            this.selectedSession = null;
            return;
          }

          console.log("received sessions from backend", data);
          this.sessions = data
          this.selectedSession = this.sessions[0];
          this.sessionStateManager = new SessionStateManager(this.modulService, this.selectedSession, this.sessionSignalService)
        }
      })
    } else {
      this.sessionApiService.getSession(sessionId).subscribe({
        next: (data) => {
          if (!data) {
            console.log("Keine Sessions vorhanden (204 No Content)");
            this.sessions = null;
            this.selectedSession = null;
            return;
          }

          console.log("received sessions from backend", data);
          this.sessions = [data]
          this.selectedSession = this.sessions[0];
          this.sessionStateManager = new SessionStateManager(this.modulService, this.selectedSession, this.sessionSignalService)
        }
      })
    }

  }

  startSession(): void {
    this.sessionStateManager.start()
    this.sessionStarted = false;
    // Wenn eine Lern-Session gestartet wurde, darf nicht währenddessen die Session-Konfiguration gewechselt werden!
    this.sessionSelectIsDisabled = true;
  }

  pauseSession(): void {
    this.sessionStateManager.pause()
    this.sessionPaused = true;
  }

  resumeSession(): void {
    this.sessionStateManager.fortsetzen()
    this.sessionPaused = false;
  }

  abortThisSession() : void {
    this.sessionAbgebrochen = true
    this.sessionStateManager.beenden();
    this.sessionSignalService.resetThisSession()
    this.sessionStarted = true;
    // Nach einem Abbruch der Session können wieder andere Konfigurationen gewählt werden
    this.sessionSelectIsDisabled = false;
    this.ngOnInit()
  }

  getCurrentLernzeit(): number {
    return this.sessionStateManager.aktuelleLernzeit
  }

  getCurrentPause(): number {
    return this.sessionStateManager.aktuellePausenzeit
  }

  getCurrentBlockId(): string {
    return this.sessionStateManager.aktuelleBlockId
  }

  selectSession(selectedSession : Session) {
    // Setze die ausgewählte Session als globale ausgewählte Session
    this.selectedSession = selectedSession
    // Setze für den UI-State die aktuelle Fach-Id des Blocks neu
    this.sessionStateManager.aktuelleBlockId = this.selectedSession.blocks[0].fachId
    // Der State-Manager wird mit deer global gesetzen Session neu instanziiert
    this.sessionStateManager = new SessionStateManager(this.modulService, this.selectedSession, this.sessionSignalService)
  }

  getComputedSessionZeit(session : Session) : number {
    let totalSessionZeit: number | undefined = 0
    for(let i = 0; i < (session?.blocks?.length ?? 0); i++) {
      totalSessionZeit += (session?.blocks?.[i]?.lernzeitSeconds ?? 0) + (session?.blocks?.[i]?.pausezeitSeconds ?? 0)
    }
    return totalSessionZeit
  }

  resetButtons() {
    this.sessionStarted = true
    this.sessionPaused = false
    this.sessionSelectIsDisabled = false
  }

  openRatingModal() {
    const modalEl = document.getElementById('rating-modal');
    if (modalEl) {
      const modal = new Modal(modalEl);
      modal.show();
    }
  }

  sendBewertung() {
    this.sessionApiService.sendSessionBewertung(this.sessionBewertung, this.sessionAbgebrochen).subscribe({
      next: (data) => {
        console.log("Session Bewertung gespeichert", data)
        // Schließe das Modal nach dem Absenden der Bewertung
        const modalEl = document.getElementById('rating-modal');
        if (modalEl) {
          const modal = Modal.getInstance(modalEl);
          modal?.hide();
        }
        // Setze die Bewertung zurück
        this.sessionBewertung = {konzentrationBewertung: 0, produktivitaetBewertung: 0, schwierigkeitBewertung: 0}
        this.sessionAbgebrochen = false
      },
      error: (error) => {
        console.error("Fehler beim Speichern der Session Bewertung", error)
      }
    })
  }

  /**
   * Überspringt den aktuellen Lernblock und fährt mit dem nächsten fort.
   * Die bis dahin gelernte Zeit wird gespeichert.
   */
  skipLernblock() {
    this.sessionStateManager.ueberspringen()
  }

  ueberspringbar() : boolean {
    return this.sessionStateManager.blockIstUberspringbar()
  }
}
