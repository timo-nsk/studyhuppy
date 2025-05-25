import {Kreditpunkte} from './kreditpunkte';
import {Semester} from './semester';
import {Lerntage} from './lerntage';

export interface Modul {
  id:             null;
  fachId:         string;
  name:           string;
  secondsLearned: number;
  kreditpunkte:   Kreditpunkte;
  username:       string;
  active:         boolean;
  semesterstufe:  number;
  semester:       Semester;
  klausurDate:    Date;
  lerntage:       Lerntage | undefined;
}
