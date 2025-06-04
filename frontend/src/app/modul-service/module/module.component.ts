import {Component, OnInit, inject} from '@angular/core';
import {Modul} from './domain';
import { CommonModule } from '@angular/common';
import { TimeFormatPipe } from './time-format.pipe';
import {MatProgressBar} from '@angular/material/progress-bar';
import {RouterLink} from '@angular/router';
import { LoggingService } from '../../logging.service';
import { ModuleApiService } from './module-api.service';

@Component({
  selector: 'app-module',
  templateUrl: './module.component.html',
  styleUrls: ['./module.component.scss', '../../loading.scss', '../../accordion.scss'],
  standalone: true,
  imports: [ CommonModule, TimeFormatPipe, MatProgressBar, RouterLink ]
})
export class ModuleComponent implements OnInit{
  service = inject(ModuleApiService)
  log : LoggingService = new LoggingService("ModuleComponent", "modul-service")
  pipe : TimeFormatPipe = new TimeFormatPipe()
  module: { [key: number]: Modul[] } = {}

  sessionSecondsLearned : number = 0;
  timer: number = 0
  running: boolean = true
  disabledBtn : boolean[][] = []
  isLoading: boolean = true
  openPanels: boolean[] = []

  ngOnInit(): void {
    this.service.getModuleByFachsemester().subscribe({
      next: (data) => {
        this.module = data
        this.isLoading = false
        this.initDisabledBtn()
        this.initOpenPanels()
        this.log.debug("Got active module")
      },
      error: (err) => {
        this.log.error(err)
      }
    });
  }

  initOpenPanels() {
    this.openPanels.push(true)
    for (let i = 1; i < Object.keys(this.module).length; i++) {
      this.openPanels.push(false)
    }
    this.log.debug("Initialized openPanels: " + this.openPanels)
  }

  initDisabledBtn() {
    //Sortieren, damit hohe Fachsemester oben angezeigt werden
    const sortedKeys = Object.keys(this.module)
      .map(key => parseInt(key, 10))
      .sort((a, b) => b - a);

    for (const key of sortedKeys) {
      if (this.module.hasOwnProperty(key)) {
        const moduleListe = this.module[key];
        let fachSemesterBtn = Array(moduleListe.length).fill(false)
        this.disabledBtn.push(fachSemesterBtn)
      }
    }

    this.log.debug("Initialized disabledBtn: " + this.disabledBtn)
  }

  setRunningBtn(i : number, j: number){
    for (let k = 0; k < this.disabledBtn.length; k++) {
      for (let l = 0; l < this.disabledBtn[k].length; l++) {
        this.disabledBtn[k][l] = !(k === i && l === j);
      }
    }
    this.log.debug(`Set disabledBtn to 'true', except at index [${i}][${j}]`)
  }

  clearRunningBtn() {
    for (let i = 0; i < this.disabledBtn.length; i++) {
      for (let j = 0; j < this.disabledBtn[i].length; j++) {
        this.disabledBtn[i][j] = false
      }
    }
    this.log.debug("Cleared disabledBtn array")
  }

  updateSeconds(seconds: number): number {
    this.sessionSecondsLearned++;
    return ++seconds;
  }

  updateSecondsOnModulUI(fachId: string, seconds: number): void {
    const element = document.getElementById(fachId);
    if (element) {
      element.innerText = this.pipe.transform(seconds)
      element.dataset['value'] = seconds.toString();
    }
  }

  startTimer(fachId: string, i : number, j: number) {
    let seconds : number;

    this.service.getSeconds(fachId).subscribe({ next: (data) => { seconds = data } })

    // Wurde noch kein Button betätigt wurde, dann kann Timer starten, sonst wäre disabledBtn[index]=true und es kann
    // kein weiterer Button betätigt werden
    if(!this.disabledBtn[i][j]) {
      if (this.running) {
        //this.log.debug(`Start timer for modul '${this.module[i][j].name}'`)
        this.timer = window.setInterval(() => {
          seconds = this.updateSeconds(seconds);
          this.updateSecondsOnModulUI(fachId, seconds)
        }, 1000);
        this.switchButtonStyle(fachId, 0, i);
        this.running = false;
        this.setRunningBtn(i, j)
      } else {
        clearInterval(this.timer)
        this.clearRunningBtn()
        this.service.postNewSeconds(fachId, this.sessionSecondsLearned).subscribe()
        this.switchButtonStyle(fachId, 1, i);
        this.running = true;
        //this.log.debug(`Finished timer of modul '${this.module[i][j].name}' with sessionSecondsLearned: '${this.sessionSecondsLearned}'`)
        this.sessionSecondsLearned = 0
      }
    }
  }

  switchButtonStyle(fachId: string, flag: 0 | 1, index : number): void {
    const button = document.getElementById("btn-" + fachId)
    if (!button) return;

    const icon = button.querySelector<HTMLElement>(".button-icon-" + index);
    if (!icon) return;

    if (flag === 1) {
      button.classList.remove("stop");
      button.classList.remove("stop-button");
      button.classList.add("play");
      button.classList.add("play-button");
    } else if (flag === 0) {
      button.classList.add("stop");
      button.classList.add("stop-button");
      button.classList.remove("play");
      button.classList.remove("play-button");
    }
  }

  isTodayLerntag(modul : Modul) : boolean {
    if(modul.lerntage) {
      let lerntage : boolean[] = modul.lerntage.allLerntage;
      let dayOfWeek = new Date().getDay().valueOf();
      return lerntage[dayOfWeek]
    }
    return false
  }

  getModuleForSemester(semester: number): Modul[] {
    return this.module[semester] || [];
  }

  showAccordionElement(i : number) {
    this.openPanels[i] = !this.openPanels[i];
  }

  protected readonly Object = Object;
}
