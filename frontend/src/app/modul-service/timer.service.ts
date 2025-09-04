export interface TimerRequest {
  modulId : string
  startDateMillis : string;
}

export class TimerService {

  modulId : string;

  constructor(modulId : string) {
    this.modulId = modulId
  }

  setLogicTimer() {
    if(this.modulTimerHasNotAlreadyStarted()) {
      let startDate = Date.now().toString()
      localStorage.setItem(this.modulId, startDate)
    }
  }

  getTimerRequest() : TimerRequest | undefined {
    if(this.modulTimerHasNotAlreadyStarted()) {
      let startDateMillis = localStorage.getItem(this.modulId)!
      const modulId = this.modulId
      let request : TimerRequest = {modulId, startDateMillis}
      this.clearLogicTimer()
      return request
    }
    return undefined
  }

  modulTimerHasNotAlreadyStarted(): boolean {
    const uuidRegex = /^[0-9a-f]{32}$/i;

    for (let i = 0; i < localStorage.length; i++) {
      const key = localStorage.key(i);
      if (key && uuidRegex.test(key)) {
        const value = localStorage.getItem(key);
        if (value && value === this.modulId) {
          // der Timer für genau dieses Modul läuft schon
          return false;
        } else {
          // ein anderer Timer läuft, aber maxmal 1 Timer erlaubt
          return false;
        }
      }
    }

    return true;
  }

  computeCurrentTimerDelta() {
    const start = parseInt(localStorage.getItem(this.modulId)!, 10);
    const now = Date.now();
    return Math.floor((now - start) / 1000);
  }

  clearLogicTimer() {
    localStorage.removeItem(this.modulId)
  }
}
