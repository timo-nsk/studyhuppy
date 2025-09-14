import {inject, Injectable} from '@angular/core';
import {ModuleApiService} from '../modul-service/module/module-api.service';
import {KarteiApiService} from '../kartei-service/kartei.api.service';
import {PlanApiService} from '../plan-service/plan-api.service';
import {forkJoin, Observable} from 'rxjs';
import {SessionApiService} from '../session-service/session-api.service';

export interface UserModulServiceInformation {
  hasModule : boolean;
  hasLernSessions : boolean;
  hasLernplan : boolean;
}

export interface UserKarteiServiceInformation {
  hasKarteikartenStapel : boolean;
  faelligeStapel : string[];
}

@Injectable({
  providedIn: "root"
})
export class HomeService {
  modulApiService = inject(ModuleApiService)
  sessionApiService = inject(SessionApiService)
  lerplanApiService = inject(PlanApiService)

  karteiApiService = inject(KarteiApiService)

  gatherUserServiceInformation(): Observable<UserModulServiceInformation> {
    return forkJoin({
      hasModule: this.modulApiService.hasModule(),
      hasLernSessions: this.sessionApiService.hasLernSessions(),
      hasLernplan: this.lerplanApiService.hasLernplan(),
    });
  }

  gatherUserKarteiServiceInformation(): Observable<UserKarteiServiceInformation> {
    return forkJoin({
      hasKarteikartenStapel: this.karteiApiService.hasKarteikartenStapel(),
      faelligeStapel: this.karteiApiService.getFaelligeStapelInfo()
    });
  }
}
