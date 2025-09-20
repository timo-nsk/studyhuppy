import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import {Lernplan} from '../plan-domain';
import {PlanApiService} from '../plan-api.service';

@Injectable({ providedIn: 'root' })
export class LernplanResolver implements Resolve<Lernplan> {
  constructor(private lernplanApiService: PlanApiService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Lernplan> {
    const fachId = route.paramMap.get('lernplanId')!;
    console.log("resolved id: ", fachId);
    return this.lernplanApiService.getLernplanById(fachId);
  }
}
