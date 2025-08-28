import {Block} from '../modul-service/session/session-domain';

export interface LernplanRequest {
  lernplanTitel : string,
  tage : TagDto[]
}

export interface LernplanResponse {
  lernplanTitel : string,
  sessionList : LernplanSessionInfoDto[]
}

export interface LernplanSessionInfoDto {
  weekday : string,
  sessionId : string,
  blocks : Block[]
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
