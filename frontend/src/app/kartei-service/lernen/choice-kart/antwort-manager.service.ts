import {Antwort} from '../../domain';

export class AntwortManager {

  userAntworten : boolean[] = []
  expectedAntworten : Antwort[]
  result : boolean[] = []

  constructor(n : number | undefined, exptectedAntworten : Antwort[] | undefined) {
    const c = n ?? 0
    for(let i = 0; i < c; i++) {
      this.userAntworten.push(false)
    }
    console.log("antwort manager init with userAntworten: " + this.userAntworten)

    this.expectedAntworten = exptectedAntworten ?? []
  }

  setAntwort(i : number) : void {
    this.userAntworten[i] = !this.userAntworten[i]
    //console.log("user set antwort at index " + i + " to: " + this.userAntworten[i])
  }

  compareAntworten() : boolean[] {
    if(this.userAntworten) {
      for(let i = 0; i < this.userAntworten.length; i++) {
        let expectedAntwort = this.expectedAntworten[i].wahrheit
        let actualAntwort = this.userAntworten[i]
        if(expectedAntwort && actualAntwort) {
          this.result.push(true)
        } else if(expectedAntwort && !actualAntwort) {
          this.result.push(false)
        } else if(!expectedAntwort && !actualAntwort) {
          this.result.push(true)
        } else if(!expectedAntwort && actualAntwort) {
          this.result.push(false)
        }
      }
    }

    console.log("compared antworten with result: " + this.result)
    return this.result
  }
  // true : true -> truesArecorrect true
  // true : false -> truesArecorrect false
  // false : false -> truesAreCorrect true
  // false : true -> truesAreCorrect false
}
