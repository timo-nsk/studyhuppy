import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HeaderService} from '../../../header.service';
import {Observable} from 'rxjs';
import {SystemHealth} from './system-health';
import { environment } from '../../../../environments/environment'

@Injectable({
  providedIn: "root"
})
export class ActuatorApiService {
  BASE_API_URL = environment.actuatorServiceUrl
  http : HttpClient = inject(HttpClient)
  headerService = inject(HeaderService)

  getSystemHealth(): Observable<SystemHealth[]> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<SystemHealth[]>(this.BASE_API_URL + '/get-system-health', {headers})
  }
}
