import {Session} from './session-domain';
import {ModuleApiService} from '../modul-service/module/module-api.service';
import {SessionSignalService} from './start/session-signal.service';
import {LernzeitenState, PausenzeitenState, ZeitTyp} from './start/timer-state';

export class SessionStateManager {
  modulService : ModuleApiService;
  lernzeitenState : LernzeitenState = {} as LernzeitenState;
  pausenzeitenState : PausenzeitenState = {} as PausenzeitenState;
  session : Session | null

  currentBlockId : string
  currentBlockIndex : number = 0;
  currentBlockLernzeit : number = 0;
  nBlocks = 0;
  notPostedSeconds = true

  constructor(modulService: ModuleApiService, session : Session | null, private sessionSignalService : SessionSignalService) {
    this.modulService = modulService
    this.lernzeitenState = new LernzeitenState(session!.blocks.map(block => block.lernzeitSeconds), this.currentBlockIndex, ZeitTyp.LERNEN);
    this.pausenzeitenState = new PausenzeitenState(session!.blocks.map(block => block.pausezeitSeconds), this.currentBlockIndex, ZeitTyp.PAUSE);

    if(this.validZeiten()) {
      this.nBlocks = this.lernzeitenState.arrayLaenge
      this.currentBlockId = session!.blocks[this.currentBlockIndex].fachId || "";
      this.session = session
    } else {
      throw new Error("Die Anzahl der Lernzeiten stimmt nicht mit der Anzahl der Pausenzeiten überein.")
    }
  }

  /**
   * Initialisiert die Lernzeiten- und Pausenzeiten-State-Objekte neu mit dem aktuell logischen Block-Index.
   */
  initialisiereTimerStates() : void {
    this.lernzeitenState = new LernzeitenState(this.session!.blocks.map(block => block.lernzeitSeconds), this.currentBlockIndex, ZeitTyp.LERNEN);
    this.pausenzeitenState = new PausenzeitenState(this.session!.blocks.map(block => block.pausezeitSeconds), this.currentBlockIndex, ZeitTyp.PAUSE);
  }

  /**
   * Leitet den Beginn der Session ein, wenn der User auf den Button "Session starten" den start-component klickt.
   */
  async start() {
    this.lernzeitenState.printState()

    //Solange noch nicht alle Blöcke abgeschlossen wurden
    while(this.sessionNichtAbgeschlossen()) {
      // 1. Wenn der Lernzeit-Timer für den aktuellen Block noch nicht beendet wurde, starte ihn
      if(this.lernzeitenState.istNochNichtGestartet) {
        this.lernzeitenState.starteTimer()
        this.currentBlockLernzeit = this.lernzeitenState.zeitenSekunden[this.currentBlockIndex]
      }

      // 2. Wenn der Lernzeit-Timer für den aktuellen Block beendet wurde, starte den Pausen-Timer
      if(this.lernzeitenState.istBeendet) {
        // 3. Sende die gelernten Sekunden an das Backend
        if(this.notPostedSeconds) {
          this._postRawSeconds()
          this.notPostedSeconds = false
        }

        // 4. Wenn der Pausen-Timer für den aktuellen Block noch nicht gestartet wurde, starte ihn
        if (this.pausenzeitenState.istNochNichtGestartet) this.pausenzeitenState.starteTimer()

        // 5. Wenn der Pausen-Timer für den aktuellen Block beendet wurde, verarbeite den nächsten Block
        if (this.pausenzeitenState.istBeendet) {
          this.notPostedSeconds = true
          this.processNextBlock()
          this.initialisiereTimerStates()
        }
      }

      await this.sleep();
    }

    // Session ist logisch abgeschlossen und der Service bekommt das Signal,
    // dass die Session beendet ist, um die UI updaten zu können
    this.sessionSignalService.finishThisSession()
  }

  /**
   * Pausiert den aktuell logischen laufenden Timer, anhand des Blockzustand.
   * Ein logischer Timer ist der aktuelle Timer, dessen Status "Laufend" ist.
   */
  pause() : void {
    if(this.lernzeitenState.istLaufend) {
     this.lernzeitenState.pausiereTimer()
    } else if (this.pausenzeitenState.istLaufend) {
      this.pausenzeitenState.pausiereTimer()
    }
  }

  /**
   * Prüft, welcher Timer pausiert ist und setzt diesen fort. Es kann logisch nur ein Timer pausiert sein.
   */
  fortsetzen() : void {
    if(this.lernzeitenState.istPausiert) {
      this.lernzeitenState.starteTimer()
    } else if (this.pausenzeitenState.istPausiert) {
      this.pausenzeitenState.starteTimer()
    }
  }

  /**
   * Prüft, ob ein Timer läuft oder pausiert ist und beendet diesen. Es ist logisch nur ein Timer laufend/pausiert.
   */
  beenden() : void {
    this._postRawSeconds()
    this.lernzeitenState.beendeTimer()
    this.pausenzeitenState.beendeTimer()
    this.forciereSessionEnde()
  }

  ueberspringen() : void {
    this._postRawSeconds()
    let processed = this.processNextBlock()

    if(processed) {
      this.initialisiereTimerStates()
      this.start()
    }
  }

  blockIstUberspringbar() : boolean {
    return this.currentBlockIndex + 1 < this.nBlocks
  }

  /**
   * Gibt zurück, ob die Anzahl der Lernzeiten mit der Anzahl der Pausenzeiten übereinstimmt.
   */
  validZeiten() : boolean {
    return this.lernzeitenState.arrayLaenge === this.pausenzeitenState.arrayLaenge;
  }

  /**
   * Setzt den nächsten logischen Block der Session, wenn noch nicht der letzte Block erreicht wurde.
   */
  processNextBlock() : boolean {
    if(this.currentBlockIndex + 1 < this.nBlocks) {
      this.currentBlockIndex++;
      this.currentBlockId = this.session!.blocks[this.currentBlockIndex].fachId || "";
      return true
    }
    return false
  }

  /**
   * Gibt zurück, ob die Session noch nicht abgeschlossen ist.
   * Eine Session ist abgeschlossen, wenn der letzte Eintrag des zeitenSekunden == 0 für den jeweiligen TimerState gilt.
   */
  sessionNichtAbgeschlossen() : boolean {
    const lernzeitenAbgeschlossen = this.lernzeitenState.letzteZeitAbgelaufen
    const pausenzeitenAbgeschlossen = this.pausenzeitenState.letzteZeitAbgelaufen
    return !(lernzeitenAbgeschlossen && pausenzeitenAbgeschlossen)
  }

  /**
   * Forciert das Session-Ende, indem die letzten Einträge der zeitenSekunden-Arrays auf 0 gesetzt werden.
   */
  forciereSessionEnde() : void {
    const n = this.lernzeitenState.arrayLaenge
    this.lernzeitenState.zeitenSekunden[n-1] = 0
    this.pausenzeitenState.zeitenSekunden[n-1] = 0
  }

  getGelernteZeitInSekunden() : number {
    return this.currentBlockLernzeit - this.aktuelleLernzeit
  }

  get aktuelleLernzeit() : number {
    return this.lernzeitenState.zeitAktuell
  }

  get aktuellePausenzeit() : number {
    return this.pausenzeitenState.zeitAktuell
  }

  get aktuelleBlockId() : string {
    return this.currentBlockId;
  }

  get aktuelleBlockModulId() {
    return this.session!.blocks[this.currentBlockIndex].modulId
  }

  set aktuelleBlockId(fachId : string | undefined) {
    this.currentBlockId = fachId ?? ""
  }

  private _postRawSeconds() : void {
    this.modulService.postRawSeconds(
      this.aktuelleBlockModulId!,
      this.getGelernteZeitInSekunden()
    ).subscribe({
      next: (response) => {
        console.log("Erfolgreich Sekunden gepostet:", response);
      },
      error: (error) => {
        console.error("Fehler beim Posten der Sekunden:", error);
      }
    })
  }

  private sleep() {
    return new Promise(resolve => setTimeout(resolve, 1000));
  }
}
