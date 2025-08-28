import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HeaderService} from '../header.service';
import {HttpClient} from '@angular/common/http';
import {LernplanRequest} from './plan-domain';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlanApiService {
  private PLAN_BASE_API = environment.planApiUrl
  private headerService = inject(HeaderService)
  private http = inject(HttpClient)

  saveLernplan(lernplan : LernplanRequest) : Observable<any>{
    const header = this.headerService.createAuthHeader()
    return this.http.post<any>(this.PLAN_BASE_API + '/create', lernplan, {headers: header})
  }

}
