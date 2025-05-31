import {Component, OnInit} from '@angular/core';
import {ModuleService} from './module-service';
import {Modul} from './domain';
import { CommonModule } from '@angular/common';
import { TimeFormatPipe } from './time-format.pipe';
import {MatProgressBar} from '@angular/material/progress-bar';
import {RouterLink} from '@angular/router';
import { LoggingService } from '../../logging.service';

@Component({
  selector: 'app-module',
  templateUrl: './module.component.html',
  styleUrls: ['./module.component.scss', '../../loading.scss'],
  standalone: true,
  imports: [
    CommonModule,
    TimeFormatPipe,
    MatProgressBar,
    RouterLink
  ]
})
export class ModuleComponent implements OnInit{
  log : LoggingService = new LoggingService("ModuleComponent", "modul-service")
  pipe : TimeFormatPipe = new TimeFormatPipe()
  module: Modul[] = [];
  sessionSecondsLearned : number = 0;
  timer: number = 0
  running: boolean = true
  disabledBtn : boolean [] = []
  isLoading: boolean = true

  constructor(private service: ModuleService) {}

  ngOnInit(): void {
    this.service.getActiveModuleByUsername().subscribe({
      next: (data) => {
        this.module = data;
        this.isLoading = false
        this.initRunningBtn()
        this.log.debug("Got active module for user")
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  initRunningBtn() {
    for(let i = 0; i < this.module.length; i++) {
      this.disabledBtn.push(false)
    }
  }

  setRunningBtn(index : number){

    for (let i = 0; i < this.module.length; i++) {
      if(i != index) {
        this.disabledBtn[i] = true
      }
    }
    //console.log("modul " + this.module[index].name + " started running (" + this.disabledBtn + ")")
  }

  clearRunningBtn() {
    for(let i = 0; i < this.module.length; i++) {
      this.disabledBtn[i] = false
    }
    //console.log("stopped running modul")
  }

  updateSeconds(seconds: number): number {
    seconds++;
    this.sessionSecondsLearned++;
    return seconds;
  }

  updateSecondsOnModulUI(fachId: string, seconds: number): void {
    const element = document.getElementById(fachId);
    if (element) {
      element.innerText = this.pipe.transform(seconds)
      element.dataset['value'] = seconds.toString();
    }
  }


  async startTimer(fachId: string, index : number): Promise<void> {
    let seconds : number;

    this.service.getSeconds(fachId).subscribe({
      next: (data) => {
        seconds = data
      }
    })

    // Wurde noch kein Button betätigt wurde, dann kann Tiemr starten, sonst wäre disabledBtn[index]=true und es kann
    // kein weiterer Button betätigt werden
    if(this.disabledBtn[index] == false) {
      if (this.running) {
        this.timer = window.setInterval(() => {
          seconds = this.updateSeconds(seconds);
          this.updateSecondsOnModulUI(fachId, seconds)
        }, 1000);
        this.switchButtonStyle(fachId, 0, index);
        this.running = false;
        this.setRunningBtn(index)
      } else {
        clearInterval(this.timer)
        this.clearRunningBtn()
        this.service.postNewSeconds(fachId, this.sessionSecondsLearned).subscribe()
        this.sessionSecondsLearned = 0
        this.switchButtonStyle(fachId, 1, index);
        this.running = true;
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
}
function provideLogger(arg0: { level: any; serverLogLevel: any; }): import("@angular/core").Provider[] | undefined {
    throw new Error('Function not implemented.');
}

