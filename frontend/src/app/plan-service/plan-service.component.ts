import {Component, inject, OnInit} from '@angular/core';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {PlanApiService} from './plan-api.service';
import {LernplanResponse, LernplanSessionInfoDto} from './plan-domain';
import {TimeFormatPipe} from '../modul-service/module/time-format.pipe';

@Component({
  selector: 'app-plan-service',
  imports: [
    NgIf,
    RouterLink,
    NgForOf,
    TimeFormatPipe,
    NgClass
  ],
  templateUrl: './plan-service.component.html',
  standalone: true,
  styleUrls: ['./plan-service.component.scss', '../general.scss', '../button.scss']
})
export class PlanServiceComponent implements OnInit{
  planApiService = inject(PlanApiService)
  activeLernplan : LernplanResponse = {} as LernplanResponse;
  activePlanExists: boolean = false;
  weekdayInfos: LernplanSessionInfoDto[] = [];
  weekContainerDisabled: boolean[] = [true, true, true, true, true, true, true];

  ngOnInit(): void {
    this.disableWeekContainer()
    this.planApiService.getActiveLernplan().subscribe({
      next: (response) => {
        this.activeLernplan = response;
        this.initWeekdayInfos();
        this.activePlanExists = true;
        console.log(this.activeLernplan.lernplanTitel)
      },
      error: (status) => {
        if (status == 404) {
          this.activePlanExists = false;
          console.error('No active lernplan found for user');
        }
      }
    });
  }

  private initWeekdayInfos() {
    let sessionList = this.activeLernplan.sessionList;
    this.weekdayInfos.push(sessionList.find(session => session.weekday === 'Montags') || {
      weekday: "Montags",
      sessionId: "",
      blocks: []
    });
    this.weekdayInfos.push(sessionList.find(session => session.weekday === 'Dienstags') || {
      weekday: "Dienstags",
      sessionId: "",
      blocks: []
    });
    this.weekdayInfos.push(sessionList.find(session => session.weekday === 'Mittwochs') || {
      weekday: "Mittwochs",
      sessionId: "",
      blocks: []
    });
    this.weekdayInfos.push(sessionList.find(session => session.weekday === 'Donnerstags') || {
      weekday: "Donnerstags",
      sessionId: "",
      blocks: []
    });
    this.weekdayInfos.push(sessionList.find(session => session.weekday === 'Freitags') || {
      weekday: "Freitags",
      sessionId: "",
      blocks: []
    });
    this.weekdayInfos.push(sessionList.find(session => session.weekday === 'Samstags') || {
      weekday: "Samstags",
      sessionId: "",
      blocks: []
    });
    this.weekdayInfos.push(sessionList.find(session => session.weekday === 'Sonntags') || {
      weekday: "Sonntags",
      sessionId: "",
      blocks: []
    });
  }

  totalLearningTimePerDay(weekdayInfo: LernplanSessionInfoDto) {
    let total = 0;
    for (const block of weekdayInfo.blocks) {
      total += block.lernzeitSeconds + block.pausezeitSeconds;
    }
    return total;
  }

  disableWeekContainer() {
    let todayDate = new Date();
    let weekday = todayDate.getDay(); // 0 = Sunday, 1 = Monday, ..., 6 = Saturday

    // Umrechnung: Montag = 0, ..., Sonntag = 6
    weekday = (weekday + 6) % 7;

    for (let i = 0; i < this.weekContainerDisabled.length; i++) {
      this.weekContainerDisabled[i] = (i !== weekday);
    }

    console.log(this.weekContainerDisabled);
  }
}
