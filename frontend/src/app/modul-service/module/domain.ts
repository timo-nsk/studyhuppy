export interface Modul {
  id:             null;
  fachId:         string;
  name:           string;
  secondsLearned: number;
  kreditpunkte:   Kreditpunkte;
  username:       string;
  active:         boolean;
  semesterstufe:  number;
  semester:       Semester | undefined;
  klausurDate:    Date;
  lerntage:       Lerntage | undefined;
}

export interface Kreditpunkte {
  anzahlPunkte:         number;
  kontaktzeitStunden:   number;
  selbststudiumStunden: number;
}

export interface Lerntage {
  mondays:       boolean;
  tuesdays:      boolean;
  wednesdays:    boolean;
  thursdays:     boolean;
  fridays:       boolean;
  saturdays:     boolean;
  sundays:       boolean;
  semesterPhase: string;
  allLerntage:   boolean[];
}

export interface Semester {
  modul:           number;
  semesterTyp:     string;
  vorlesungBeginn: Date;
  vorlesungEnde:   Date;
  semesterBeginn:  Date;
  semesterEnde:    Date;
}
