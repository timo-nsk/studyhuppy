export interface Stapel {
  fachId?: string;
  modulFachId?: string;
  name?: string;
  beschreibung?: string;
  lernIntervalle?: string;
  username?: string;
  karteikarten?: Karteikarte[];
}



export interface Karteikarte {
  fachId?: string;               // UUID als String
  frage?: string;
  antwort?: string;
  antworten?: Antwort[];         // Array von möglichen Antworten
  erstelltAm?: string;           // z.B. ISO 8601 Format für LocalDateTime
  letzteAenderungAm?: string;
  faelligAm?: string;
  notiz?: string;
  wasHard?: number;
  frageTyp?: FrageTyp;           // Enum-Typ
  antwortzeitSekunden?: number;
  lernstufen?: string;
}

export interface Antwort {
  antwort : string;
  wahrheit : boolean
}

export enum FrageTyp {
  NORMAL = "NORMAL",
  SINGLE_CHOICE = "SINGLE_CHOICE",
  MULTIPLE_CHOICE = "MULTIPLE_CHOICE"
}

export enum Schwierigkeit {
  EASY = "EASY",
  NORMAL = "NORMAL",
  HARD = "HARD"
}

export class ButtonData {

  constructor(private btnTitel : string, private  schwierigkeit : Schwierigkeit) {
  }
}

export interface UpdateInfo {
  stapelId : string;
  karteId : string;
  schwierigkeit : Schwierigkeit;
  secondsNeeded : number
}

