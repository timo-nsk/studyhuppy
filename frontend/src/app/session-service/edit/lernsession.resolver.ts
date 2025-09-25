import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import {SessionApiService} from '../session-api.service';
import {Session} from '../session-domain';

@Injectable({ providedIn: 'root' })
export class LernsessionResolver implements Resolve<Session> {
  constructor(private sessionApiService: SessionApiService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Session> {
    const fachId = route.paramMap.get('lernsessionId')!;
    return this.sessionApiService.getSession(fachId);
  }
}
