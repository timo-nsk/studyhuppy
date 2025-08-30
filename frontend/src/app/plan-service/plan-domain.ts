import {Block} from '../modul-service/session/session-domain';

export class Lernplan {
  fachId : string;
  username : string;
  titel : string;
  tagesListe : TagDto[];
  isActive : boolean;

  constructor(fachId: string, username: string, titel: string, tagesListe: TagDto[], isActive: boolean) {
    this.fachId = fachId;
    this.username = username;
    this.titel = titel;
    this.tagesListe = tagesListe;
    this.isActive = isActive;
  }
}

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
  beginn: string,
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
