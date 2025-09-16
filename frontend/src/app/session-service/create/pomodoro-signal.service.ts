import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
/**
 * block-component reagiert auf Signaländerung, um alle Lern- und Pausenzeiten zu setzen
 */
export class PomodoroSignalService {
  pomodoroSignal = signal(-1);

  changeSignal() {
    this.pomodoroSignal.set(this.pomodoroSignal() * -1);
  }
}
