import {ButtonData, Karteikarte, Schwierigkeit} from './domain';

export class ButtonDataGenerator {
  buttonDataList : ButtonData[] = []
  constructor(private karteikarte: Karteikarte | undefined) {
  }

  generateButtons() : ButtonData[] {
    const splittedIntervalle = (this.karteikarte?.lernstufen as string)?.split(',');

    const schwierigkeiten: Schwierigkeit[] = [
      Schwierigkeit.HARD,
      Schwierigkeit.NORMAL,
      Schwierigkeit.EASY
    ];

    for(let i = 0; i <=2; i++) {
      if (splittedIntervalle) {
        let data: ButtonData = new ButtonData(splittedIntervalle[i], schwierigkeiten[i])
        this.buttonDataList.push(data)
      }
    }

    return this.buttonDataList
  }
}
