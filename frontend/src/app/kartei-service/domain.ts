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
  NORMAL = "Normal",
  SINGLE_CHOICE = "Single Choice",
  MULTIPLE_CHOICE = "Multiple Choice"
}

