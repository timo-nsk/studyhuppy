export interface GeneralStatistics {
  totalStudyTime : number;
  totalStudyTimePerSemester : { [key: number]: number };
  durchschnittlicheLernzeitProTag : number;
  numberActiveModules : number;
  numberNotActiveModules : number;
  maxStudiedModul  : string;
  minStudiedModul : string;
}
