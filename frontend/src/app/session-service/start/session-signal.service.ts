import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: "root"
})
export class SessionSignalService {
  sessionFinished = signal(1)

  /**
   * Läuft der letzte Block einer Session aus, wird der Wert von sessionFinished negiert, um das Ende dieser Session zu signalisieren.
   */
  finishThisSession() {
    this.sessionFinished.set(this.sessionFinished() * -1)
  }

  resetThisSession() {
    this.sessionFinished.set(1)
  }

}
