import {Antwort, FrageTyp} from '../../domain';
import {LoggingService} from '../../../logging.service';

export class AntwortManager {
  log = new LoggingService("AntwortManager", "kartei-service")

  userAntworten : boolean[] = []
  expectedAntworten : Antwort[]
  result : boolean[] = []

  constructor(n : number | undefined, exptectedAntworten : Antwort[] | undefined) {
    const c = n ?? 0
    for(let i = 0; i < c; i++) {
      this.userAntworten.push(false)
    }
    this.expectedAntworten = exptectedAntworten ?? []
  }

  setAntwort(i : number) : void {
    this.userAntworten[i] = !this.userAntworten[i]
    this.log.debug(`set antwort in AntwortManager at index=${i}`)}

  compareAntworten(frageTyp : FrageTyp | undefined) : boolean[] {
    this.log.debug(`expected antworten: ${this.expectedAntworten}`)
    this.log.debug(`actual antworten: ${this.userAntworten}`)
    if(this.userAntworten) {
      for(let i = 0; i < this.userAntworten.length; i++) {
        let expectedAntwort = this.expectedAntworten[i].wahrheit
        let actualAntwort = this.userAntworten[i]
        switch (frageTyp) {
          case FrageTyp.SINGLE_CHOICE: {
            if((expectedAntwort && actualAntwort) || (!expectedAntwort && !actualAntwort)) {
              this.result.push(true)
            } else {
              this.result.push(false)
            }
            break
          }
          case FrageTyp.MULTIPLE_CHOICE: {
            if(expectedAntwort && actualAntwort) {
              this.result.push(true)
            } else if(expectedAntwort && !actualAntwort) {
              this.result.push(false)
            } else if(!expectedAntwort && !actualAntwort) {
              this.result.push(true)
            } else if(!expectedAntwort && actualAntwort) {
              this.result.push(false)
            }
            break
          }
        }
      }
    }

    this.log.debug(`compared antworten with result: ${this.result}`)
    return this.result
  }
}
