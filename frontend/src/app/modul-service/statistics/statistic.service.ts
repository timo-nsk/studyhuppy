import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HeaderService} from '../../header.service';
import {Observable} from 'rxjs';
import { environment } from '../../../environments/environment'
import {LoggingService} from '../../logging.service';

@Injectable({
  providedIn: "root"
})
export class StatisticApiService {
  BASE_API_URL : string = environment.modulServiceUrl
  log = new LoggingService("StatisticApiService", "modul-service")
  http = inject(HttpClient)
  headerService = inject(HeaderService)

  getTotalStudyTime(): Observable<number> {
    this.log.info("Try get total study time...")
    return this.http.get<number>(
      this.BASE_API_URL + "/get-total-study-time",
      {
        headers: this.headerService.createAuthHeader()
      }
    );
  }

  getDurchschnittlicheLernzeitProTag(): Observable<number> {
    this.log.info("Try get average study time per day...")
    return this.http.get<number>(
      this.BASE_API_URL + "/get-average-study-time-per-day",
      {
        headers: this.headerService.createAuthHeader()
      }
    );
  }

  getTotalStudyTimeperSemester(): Observable<{ [key: number]: number }> {
    this.log.info("Try get total study time per semester...")
    const headers = this.headerService.createAuthHeader()
    return this.http.get<{ [key: number]: number }>(
      this.BASE_API_URL + "/get-total-study-time-per-semester", { headers});
  }

  getNumberActiveModules() : Observable<string> {
    this.log.info("Try get no. of active module...")
    const headers = this.headerService.createAuthHeader()
    return this.http.get(this.BASE_API_URL + "/get-number-active-module",
      {
        headers: this.headerService.createAuthHeader(),
        responseType: 'text'
      }
    );
  }

  getNumberNotActiveModules() : Observable<string> {
    this.log.info("Try get no. of not active module...")
    const headers = this.headerService.createAuthHeader()
    return this.http.get(this.BASE_API_URL + "/get-number-not-active-module",
      {
        headers: this.headerService.createAuthHeader(),
        responseType: 'text'
      }
    );
  }

  getMaxStudiedModul() : Observable<string> {
    this.log.info("Try get max studied modul...")
    const headers = this.headerService.createAuthHeader()
    return this.http.get(this.BASE_API_URL + "/get-max-studied-modul",
      {
        headers: this.headerService.createAuthHeader(),
        responseType: 'text'
      }
    );
  }

  getMinStudiedModul() : Observable<string> {
    this.log.info("Try get min studied module...")
    const headers = this.headerService.createAuthHeader()
    return this.http.get(this.BASE_API_URL + "/get-min-studied-modul",
      {
        headers: this.headerService.createAuthHeader(),
        responseType: 'text'
      }
    );
  }

  getChartLastDays() : Observable<{ [date: string]: { modulName: string; secondsLearned: string }[] }> {
    this.log.info("Try get chart data for last days...")
    const headers = this.headerService.createAuthHeader()
    return this.http.get<{ [date: string]: { modulName: string; secondsLearned: string }[] }>(this.BASE_API_URL + "/chart", { headers })
  }
}
