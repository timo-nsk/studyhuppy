import {Component, OnInit} from '@angular/core';
import {ModuleService} from './module-service';
import {Modul} from './modul';
import { CommonModule } from '@angular/common';
import { TimeFormatPipe } from './time-format.pipe';
import {MatProgressBar} from '@angular/material/progress-bar';
import {Lerntage} from './lerntage';

@Component({
  selector: 'app-module',
  templateUrl: './module.component.html',
  styleUrls: ['./module.component.scss', '../../loading.scss'],
  standalone: true,
  imports: [
    CommonModule,
    TimeFormatPipe,
    MatProgressBar
  ]
})
export class ModuleComponent implements OnInit{
  pipe : TimeFormatPipe = new TimeFormatPipe()
  module: Modul[] = [];
  sessionSecondsLearned : number = 0;
  timer: number = 0
  running: boolean = true
  isLoading: boolean = true

  constructor(private service: ModuleService) {}

  ngOnInit(): void {
    this.service.getActiveModuleByUsername().subscribe({
      next: (data) => {
        this.module = data;
        this.isLoading = false
        //console.log(this.module);
      },
      error: (err) => {
        console.error(err);
      }
    });
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


  async startTimer(fachId: string): Promise<void> {
    let seconds : number;

    this.service.getSeconds(fachId).subscribe({
      next: (data) => {
        seconds = data
      }
    })

    if (this.running) {
      this.timer = window.setInterval(() => {
        seconds = this.updateSeconds(seconds);
        this.updateSecondsOnModulUI(fachId, seconds)
      }, 1000);
      this.switchButtonStyle(fachId, 0);
      this.running = false;
    } else {
      clearInterval(this.timer)
      this.service.postNewSeconds(fachId, this.sessionSecondsLearned).subscribe()
      this.sessionSecondsLearned = 0
      this.switchButtonStyle(fachId, 1);
      this.running = true;
    }
  }

  switchButtonStyle(fachId: string, flag: 0 | 1): void {
    const button = document.getElementById("btn-" + fachId)
    if (!button) return;

    const icon = button.querySelector<HTMLElement>("#button-icon");
    if (!icon) return;

    const PLAY = 'fa-play';
    const STOP = 'fa-stop';

    if (flag === 1) {
      icon.classList.add(PLAY);
      icon.classList.remove(STOP);
      button.classList.remove("stop");
      button.classList.remove("stop-button");
      button.classList.add("play");
      button.classList.add("play-button");
    } else if (flag === 0) {
      icon.classList.add(STOP);
      icon.classList.remove(PLAY);
      button.classList.add("stop");
      button.classList.add("stop-button");
      button.classList.remove("play");
      button.classList.remove("play-button");
    }
  }

  isTodayLerntag(modul : Modul) : boolean {
    let lerntage : boolean[] = modul.lerntage.allLerntage;
    let dayOfWeek = new Date().getDay().valueOf();
    return lerntage[dayOfWeek]
  }
}
