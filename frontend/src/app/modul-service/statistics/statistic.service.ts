import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HeaderService} from '../../header.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: "root"
})
export class StatisticApiService {
  BASE_API_URL : string = "http://localhost:9080/statistics/api/v1"
  http = inject(HttpClient)
  headerService = inject(HeaderService)

  getTotalStudyTime() : Observable<number> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<number>(this.BASE_API_URL + "/get-total-study-time", {headers})
  }

  getNumberActiveModules() : Observable<number> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<number>(this.BASE_API_URL + "/get-number-active-module", {headers})
  }

  getNumberNotActiveModules() : Observable<number> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<number>(this.BASE_API_URL + "/get-number-not-active-module", {headers})
  }

  getMaxStudiedModul() : Observable<string> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<string>(this.BASE_API_URL + "/get-max-studied-modul", {headers})
  }

  getMinStudiedModul() : Observable<string> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<string>(this.BASE_API_URL + "/get-min-studied-modul", {headers})
  }
}
