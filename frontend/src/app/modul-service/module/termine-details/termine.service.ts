import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Modultermin} from '../domain';
import {HeaderService} from '../../../header.service';
import { environment} from '../../../../environments/environment';
import {LoggingService} from '../../../logging.service';

@Injectable({
  providedIn: "root"
})
export class ModultermineApiService {
  MODUL_BASE_API = environment.modulServiceUrl
  log = new LoggingService("ModultermineApiService", "modul-service")
  http = inject(HttpClient)
  headerService = inject(HeaderService)

  getTermine(modulId: string) : Observable<Modultermin[]> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<Modultermin[]>(this.MODUL_BASE_API + "/getModultermine", {
      headers,
      params: {modulId}})
  }

  postNeuerTermin(data : any) : Observable<any> {
    this.log.debug(`Try sending ModulTermin data=${data}...`)
    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>(this.MODUL_BASE_API + "/addModultermin", data, { headers })
  }
}
