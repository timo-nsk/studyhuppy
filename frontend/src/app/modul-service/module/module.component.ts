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
  service = inject(ModuleApiService)
  log : LoggingService = new LoggingService("ModuleComponent", "modul-service")
  pipe : TimeFormatPipe = new TimeFormatPipe()
  module: Modul[] = [];
  sessionSecondsLearned : number = 0;
  timer: number = 0
  running: boolean = true
  disabledBtn : boolean [] = []
  isLoading: boolean = true

  ngOnInit(): void {
    this.service.getActiveModuleByUsername().subscribe({
      next: (data) => {
        this.module = data;
        this.isLoading = false
        this.initDisabledBtn()
        this.log.debug("Got active module")
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  initDisabledBtn() {
    for(let i = 0; i < this.module.length; i++) {
      this.disabledBtn.push(false)
    }
    this.log.debug("Initialized disabledBtn: " + this.disabledBtn)
  }

  setRunningBtn(index : number){

    for (let i = 0; i < this.module.length; i++) {
      if(i != index) {
        this.disabledBtn[i] = true
      }
    }
    this.log.debug(`Set disabledBtn to 'true', except at index ${index}`)
  }

  clearRunningBtn() {
    for(let i = 0; i < this.module.length; i++) {
      this.disabledBtn[i] = false
    }
    this.log.debug("Cleared disabledBtn array")
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

    // Wurde noch kein Button betätigt wurde, dann kann Timer starten, sonst wäre disabledBtn[index]=true und es kann
    // kein weiterer Button betätigt werden
    if(this.disabledBtn[index] == false) {
      if (this.running) {
        this.log.debug(`Start timer for modul '${this.module[index].name}'`)
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
        this.switchButtonStyle(fachId, 1, index);
        this.running = true;
        this.log.debug(`Finished timer of modul '${this.module[index].name}' with sessionSecondsLearned: '${this.sessionSecondsLearned}'`)
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
}
