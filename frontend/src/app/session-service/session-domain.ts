export class Block {
  fachId?: string;
  modulId?: string;
  modulName: string;
  lernzeitSeconds: number;
  pausezeitSeconds: number;


  constructor(modulName: string, lernzeitSeconds: number, pausezeitSeconds: number, fachId?: string, modulId? : string) {
    this.fachId = fachId;
    this.modulId = modulId;
    this.modulName = modulName;
    this.lernzeitSeconds = lernzeitSeconds;
    this.pausezeitSeconds = pausezeitSeconds;
  }

  setModulName(modulName : string) : void {
    this.modulName = modulName
  }

  setModulId(modulId: string): void {
    this.modulId = modulId;
  }

  setLernzeitSeconds(seconds: number): void {
    this.lernzeitSeconds = seconds;
  }

  setPausezeitSeconds(seconds: number): void {
    this.pausezeitSeconds = seconds;
  }

  printBlock() : void {
    console.log(`- Block - ModulId: ${this.modulId}, Lernzeit: ${this.lernzeitSeconds}, Pausezeit: ${this.pausezeitSeconds}`);
  }
}

export class Session {
  fachId?: string;
  titel: string;
  beschreibung: string;
  blocks: Block[];

  constructor(titel: string, beschreibung: string, blocks: Block[] = [], fachId?: string) {
    this.fachId = fachId;
    this.titel = titel;
    this.beschreibung = beschreibung;
    this.blocks = blocks;
  }
}

export interface SessionInfoDto {
  fachId : string,
  name : string,
  zeit : number
}

export interface SessionBewertung {
  konzentrationBewertung : number;
  produktivitaetBewertung : number;
  schwierigkeitBewertung : number;
}
