export class LernzeitTimer {
  lernzeitGesamt : number | undefined;
  lernzeitCurrentKarte : number | undefined;
  overallTimerId: any;
  currentTimerId: any;
  sessionRunning : boolean = false
  private _anzahlKarten: number | undefined;

  constructor(anzahlKarten : number | undefined) {
    //If the object is initialized, it will start its overall timers
    if(!this.isSessionRunning) {
      this._anzahlKarten = anzahlKarten;
      this.lernzeitGesamt = 0
      this.lernzeitCurrentKarte = 0
      this.sessionRunning = true
      this.startOverallTimer();
    }
  }

  startOverallTimer(): void {
    this.overallTimerId= setInterval(() => {
      this.lernzeitGesamt!++;
    }, 1000);
    this.sessionRunning = true;
    console.log("starting OVERALL timer, id:" + this.overallTimerId);
  }

  clearOverallTimer() {
    clearInterval(this.overallTimerId);
    this.sessionRunning = false;
    console.log("-- cleared OVERALL timer");
  }

  startCurrentKarteTimer(): void {
    this.currentTimerId = setInterval(() => {
      this.lernzeitCurrentKarte!++;
      }, 1000);
    console.log("- started CURRENT timer, id:" + this.currentTimerId);
  }

  getLernzeitGesamt() : number {
    return this.lernzeitGesamt!
  }

  get getLernzeitCurrentKarte() : number {
    return  this.lernzeitCurrentKarte!
  }

  get isSessionRunning() : boolean {
    return this.sessionRunning
  }

  clearCurrentTimer() {
    console.log("-- cleared CURRENT timer, id:" + this.currentTimerId);
    this.lernzeitCurrentKarte = 0
    clearInterval(this.currentTimerId);
  }
}
