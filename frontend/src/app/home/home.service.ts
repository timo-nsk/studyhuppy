import {inject, Injectable} from '@angular/core';
import {ModuleApiService} from '../modul-service/module/module-api.service';
import {KarteiApiService} from '../kartei-service/kartei.api.service';
import {PlanApiService} from '../plan-service/plan-api.service';
import {forkJoin, Observable} from 'rxjs';
import {SessionApiService} from '../session-service/session-api.service';

export interface UserServiceInformation {
  hasModule : boolean;
  hasLernSessions : boolean;
  hasLernplan : boolean;
  hasKarteikartenStapel : boolean;
}

@Injectable({
  providedIn: "root"
})
export class HomeService {
  modulApiService = inject(ModuleApiService)
  sessionApiService = inject(SessionApiService)
  lerplanApiService = inject(PlanApiService)
  karteiApiService = inject(KarteiApiService)

  gatherUserServiceInformation(): Observable<UserServiceInformation> {
    return forkJoin({
      hasModule: this.modulApiService.hasModule(),
      hasLernSessions: this.sessionApiService.hasLernSessions(),
      hasLernplan: this.lerplanApiService.hasLernplan(),
      hasKarteikartenStapel: this.karteiApiService.hasKarteikartenStapel()
    });
  }
}
