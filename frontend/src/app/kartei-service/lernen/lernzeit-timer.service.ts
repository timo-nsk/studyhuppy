export class LernzeitTimer {
  lernzeitGesamt : number;
  lernzeitCurrentKarte : number;
  currentTimerId: any;

  constructor() {
    this.lernzeitGesamt = 0
    this.lernzeitCurrentKarte = 0
  }

  //  totalKarten = this.thisStapel.karteikarten!.length
  startOverallTimer(currentKartenIndex : number, totalKarteikarten : number | undefined): void {
    let n = currentKartenIndex ?? 0
    let m = totalKarteikarten ?? 0
    const intervalId = setInterval(() => {
      this.lernzeitGesamt++;

      if (n >= m - 1) {
        clearInterval(intervalId);
        //console.log("Timer gestoppt – letzte Karte erreicht");
      }
    }, 1000);
  }

  startCurrentKarteTimer(currentKartenIndex : number, totalKarteikarten : number | undefined, event: MouseEvent | null): void {
    let n = currentKartenIndex ?? 0
    let m = totalKarteikarten ?? 0

    if (event) {
      this.lernzeitCurrentKarte = 0;

      if (this.currentTimerId) {
        clearInterval(this.currentTimerId);
      }
    }

    this.currentTimerId = setInterval(() => {
      this.lernzeitCurrentKarte++;
      //console.log("Sekunden für aktuelle Karte:", this.lernzeitCurrentKarte);
    }, 1000);

    if (n >= m  - 1) {
      clearInterval(this.currentTimerId);
      //console.log("Timer gestoppt – letzte Karte erreicht");
    }
  }

  get getLernzeitGesamt() : number {
    return this.lernzeitGesamt
  }

  get getLernzeitCurrentKarte() : number {
    return  this.lernzeitCurrentKarte
  }
}
