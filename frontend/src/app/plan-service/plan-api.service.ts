import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HeaderService} from '../header.service';
import {HttpClient} from '@angular/common/http';
import {Lernplan, LernplanBearbeitetRequest, LernplanRequest, LernplanResponse} from './plan-domain';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlanApiService {
  private PLAN_BASE_API = environment.planApiUrl
  private headerService = inject(HeaderService)
  private http = inject(HttpClient)

  getAllLernplaene() : Observable<any> {
    const header = this.headerService.createAuthHeader()
    return this.http.get<Lernplan[]>(this.PLAN_BASE_API + '/get-all-lernplaene', {headers: header})
  }

  saveLernplan(lernplan : LernplanRequest) : Observable<any>{
    const header = this.headerService.createAuthHeader()
    return this.http.post<any>(this.PLAN_BASE_API + '/create', lernplan, {headers: header})
  }

  getActiveLernplan() : Observable<any> {
    const header = this.headerService.createAuthHeader()
    return this.http.get<LernplanResponse>(this.PLAN_BASE_API + '/get-active-lernplan', {headers: header})
  }

  deleteLernplan(fachId: string) :Observable<any> {
    const header = this.headerService.createAuthHeader()
    return this.http.delete<any>(this.PLAN_BASE_API + `/delete-lernplan/${fachId}`, {headers: header})
  }

  setActiveLernplan(fachId: string) {
    const header = this.headerService.createAuthHeader()
    return this.http.post<any>(this.PLAN_BASE_API + `/set-active-lernplan/${fachId}`, null, {headers: header})
  }

  hasLernplan(): Observable<any> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<any>(this.PLAN_BASE_API + '/has-lernplan', {headers})
  }

  isTodayPlanned(): Observable<any> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<any>(this.PLAN_BASE_API + '/is-today-planned', {headers})
  }

  getLernplanById(lernplanId: string) : Observable<Lernplan> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<Lernplan>(this.PLAN_BASE_API + `/get-lernplan-by-id/${lernplanId}`, {headers})
  }

  sendLernplanBearbeitetRequest(lernplanBearbeitetRequest: LernplanBearbeitetRequest) {
    const header = this.headerService.createAuthHeader()
    return this.http.post<any>(this.PLAN_BASE_API + '/bearbeite-lernplan', lernplanBearbeitetRequest, {headers: header})
  }
}
