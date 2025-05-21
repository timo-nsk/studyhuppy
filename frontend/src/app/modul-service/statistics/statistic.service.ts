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

  getTotalStudyTime(): Observable<number> {
    return this.http.get<number>(
      this.BASE_API_URL + "/get-total-study-time",
      {
        headers: this.headerService.createAuthHeader()
      }
    );
  }

  getDurchschnittlicheLernzeitProTag(): Observable<number> {
    return this.http.get<number>(
      this.BASE_API_URL + "/get-average-study-time-per-day",
      {
        headers: this.headerService.createAuthHeader()
      }
    );
  }

  getTotalStudyTimeperSemester(): Observable<{ [key: number]: number }> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<{ [key: number]: number }>(
      this.BASE_API_URL + "/get-total-study-time-per-semester", { headers});
  }

  getNumberActiveModules() : Observable<string> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get(this.BASE_API_URL + "/get-number-active-module",
      {
        headers: this.headerService.createAuthHeader(),
        responseType: 'text'
      }
    );
  }

  getNumberNotActiveModules() : Observable<string> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get(this.BASE_API_URL + "/get-number-not-active-module",
      {
        headers: this.headerService.createAuthHeader(),
        responseType: 'text'
      }
    );
  }

  getMaxStudiedModul() : Observable<string> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get(this.BASE_API_URL + "/get-max-studied-modul",
      {
        headers: this.headerService.createAuthHeader(),
        responseType: 'text'
      }
    );
  }

  getMinStudiedModul() : Observable<string> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get(this.BASE_API_URL + "/get-min-studied-modul",
      {
        headers: this.headerService.createAuthHeader(),
        responseType: 'text'
      }
    );
  }

  getChartLastDays() : Observable<{ [date: string]: { modulName: string; secondsLearned: string }[] }> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<{ [date: string]: { modulName: string; secondsLearned: string }[] }>(this.BASE_API_URL + "/chart", { headers })
  }
}
