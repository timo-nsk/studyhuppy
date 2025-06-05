import {LoggingService} from '../../logging.service';

export class LernzeitTimer {
  log = new LoggingService("LernzeitTimer", "kartei-service")

  lernzeitGesamt : number | undefined;
  lernzeitCurrentKarte : number | undefined;
  overallTimerId: any;
  currentTimerId: any;
  sessionRunning : boolean = false

  constructor(private n : number) {
    //If the object is initialized, it will start its overall timers
    if(!this.isSessionRunning) {
      this.lernzeitGesamt = 0
      this.lernzeitCurrentKarte = 0
      this.sessionRunning = true
      this.startOverallTimer();
    }
  }

  startOverallTimer(): void {
    this.overallTimerId= setInterval(() => {
      this.lernzeitGesamt = (this.lernzeitGesamt ?? 0) + 1;
    }, 1000)
    this.sessionRunning = true
    this.log.info(`starting OVERALL timer, id: ${this.overallTimerId}`)
  }

  clearOverallTimer() {
    clearInterval(this.overallTimerId)
    this.sessionRunning = false
    this.log.info(`cleared OVERALL timer, id: ${this.overallTimerId}`)
  }

  startCurrentKarteTimer(): void {
    this.currentTimerId = setInterval(() => {
      this.lernzeitGesamt = (this.lernzeitGesamt ?? 0) + 1;
      }, 1000)
    this.log.info(`starting CURRENT timer, id: ${this.currentTimerId}`)
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
    this.log.info(`cleared CURRENT timer, id: ${this.currentTimerId}`)
    this.lernzeitCurrentKarte = 0
    clearInterval(this.currentTimerId);
  }
}
