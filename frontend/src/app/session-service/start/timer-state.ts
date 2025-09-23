/**
 * Mögliche logische Zustände des Timers.
 * Ein Timer ist nur bei Konstruktion AUS.
 * Wenn der Timer gestartet ist, ist er LAUFEND.
 * Ein laufender Timer kann PAUSIERT werden.
 * Wenn das zeitenSekunden[i] den Wert 0 erreicht, ist der Timer BEENDET.
 */
enum TimerStatus { AUS, LAUFEND, PAUSIERT, BEENDET}

/**
 * Typ des Timers für Debuggingzwecke
 */
export enum ZeitTyp { LERNEN = "Lernen", PAUSE = "Pause"}

/**
 * Verwaltet den Zustand eines Timers für Lern- und Pausenzeiten eines Blocks der Session.
 */
class TimerState {
  ONE_SECOND : number = 1000
  zeitenSekunden : number[] = []
  timerId : any = null
  currentIndex : number = -1
  currentStatus : TimerStatus;
  zeittyp : ZeitTyp

  constructor(zeitenSekunden : number[], currentIndex : number, zeittyp : ZeitTyp) {
    if(currentIndex < 0) throw new Error("currentIndex muss >= 0 sein")

    this.zeitenSekunden = zeitenSekunden
    this.currentIndex = currentIndex
    this.currentStatus = TimerStatus.AUS
    this.zeittyp = zeittyp
  }

  /**
   * Gibt den aktuellen Zustand des Timers in der Konsole aus.
   */
  printState() : void {
    console.log(`current status: zeittyp: ${this.zeittyp}, zeitenSekunden=${this.zeitenSekunden}, currentIndex=${this.currentIndex}, currentStatus=${this.currentStatus}`)
  }

  /**
   * Startet den Timer und setzt den Status von "Aus" zu "Laufend" und speichert der Anfangswert von zeitenSekunden[i]. Der Timer wird jede Sekunde um 1 Sekunde verringert.
   */
  starteTimer() : void {
    if(this.currentStatus === TimerStatus.AUS || this.currentStatus === TimerStatus.PAUSIERT) {
      this.currentStatus = TimerStatus.LAUFEND
      this.timerId = setInterval(() => {
        this.dekrementAktuelleZeit();

        if(this.zeitAbgelaufen()) this.beendeTimer()
        this.printState()

      }, this.ONE_SECOND)
    }
  }

  /**
   * Setzt den Timer zurück, indem der Interval gestoppt wird.
   */
  timerZuruecksetzen() : void {
    clearInterval(this.timerId)
    this.timerId = null
  }

  /**
   * Setzt den Timer zurück und versetzt ihn logisch auf den Status "Pausiert".
   */
  pausiereTimer() : void {
    this.currentStatus = TimerStatus.PAUSIERT
    this.timerZuruecksetzen()
  }

  /**
   * Beendet den Timer und signalisiert das logische Ende für den Block-Abschnitt der Session
   */
  beendeTimer() : void {
    this.currentStatus = TimerStatus.BEENDET
    this.timerZuruecksetzen()
  }

  /**
   * Gibt die aktuelle Zeit in Sekunden zurück.
   */
  get zeitAktuell() : number {
    return this.zeitenSekunden[this.currentIndex]
  }

  /**
   * Gibt den aktuellen logischen Status des Timers zurück.
   */
  get status() : TimerStatus {
    return this.currentStatus
  }

  /**
   * Gibt zurück, ob der Timer beendet ist.
   */
  get istBeendet() : boolean {
    return this.currentStatus === TimerStatus.BEENDET
  }

  /**
   * Gibt zurück, ob der Timer läuft.
   */
  get istLaufend() : boolean {
    return this.currentStatus === TimerStatus.LAUFEND
  }

  /**
   * Gibt zurück, ob der Timer pausiert ist.
   */
  get istPausiert() : boolean {
    return this.currentStatus === TimerStatus.PAUSIERT
  }

  /**
   * Gibt zurück, ob der Timer noch nicht gestartet ist.
   */
  get istNochNichtGestartet() : boolean {
    return this.currentStatus === TimerStatus.AUS
  }

  get arrayLaenge() : number {
    return this.zeitenSekunden.length
  }

  /**
   * Gibt zurück, ob die letzte Zeit im Array abgelaufen ist (d.h. ob sie 0 Sekunden erreicht hat).
   */
  get letzteZeitAbgelaufen() : boolean {
    return this.zeitenSekunden[this.arrayLaenge - 1] === 0
  }

  /**
   * Verringert die aktuelle Zeit um eine Sekunde.
   */
  private dekrementAktuelleZeit() : void {
    this.zeitenSekunden[this.currentIndex]--;
  }

  /**
   * Überprüft, ob die aktuelle Zeit abgelaufen ist (d.h. ob sie 0 Sekunden erreicht hat).
   */
  private zeitAbgelaufen() : boolean {
    return this.zeitenSekunden[this.currentIndex] === 0
  }
}

export class LernzeitenState extends TimerState {
  constructor(zeitenSekunden : number[], currentIndex : number, zeittyp : ZeitTyp) {
    super(zeitenSekunden, currentIndex, zeittyp);
  }
}

export class PausenzeitenState extends TimerState {
  constructor(zeitenSekunden : number[], currentIndex : number, zeittyp : ZeitTyp) {
    super(zeitenSekunden, currentIndex, zeittyp);
  }
}
