import {Antwort, FrageTyp} from '../../domain';

export class AntwortManager {

  userAntworten : boolean[] = []
  expectedAntworten : Antwort[]
  result : boolean[] = []

  constructor(n : number | undefined, exptectedAntworten : Antwort[] | undefined) {
    const c = n ?? 0
    for(let i = 0; i < c; i++) {
      this.userAntworten.push(false)
    }
    //console.log("antwort manager init with userAntworten: " + this.userAntworten)

    this.expectedAntworten = exptectedAntworten ?? []
  }

  setAntwort(i : number) : void {
    this.userAntworten[i] = !this.userAntworten[i]
    //console.log("user set antwort at index " + i + " to: " + this.userAntworten[i])
  }

  compareAntworten(frageTyp : FrageTyp | undefined) : boolean[] {
    //console.log("expected: " + this.expectedAntworten)
    //console.log("actual: " + this.userAntworten)
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
            // false : true -> result false
            // true : false -> result false
            // false : false -> result true
            // true : true -> result true
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

    //console.log("compared antworten with result: " + this.result)
    return this.result
  }

}
