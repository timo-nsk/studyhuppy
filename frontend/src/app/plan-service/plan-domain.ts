import {FormGroup} from '@angular/forms';

export interface LernplanRequest {
  lernplanTitel : string,
  tage : TagDto[]
}

export class TagDto {
  weekday : string;
  beginn : string;
  session : string;

  constructor(weekday: string, beginn: string, session: string) {
    this.weekday = weekday;
    this.beginn = beginn;
    this.session = session;
  }
}
