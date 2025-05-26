import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Modultermin} from '../domain';
import {HeaderService} from '../../../header.service';

@Injectable({
  providedIn: "root"
})
export class ModultermineApiService {
  private MODUL_BASE_API = 'http://localhost:9080/api';

  http = inject(HttpClient)
  headerService = inject(HeaderService)

  getTermine(modulId: string) : Observable<Modultermin[]> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<Modultermin[]>(this.MODUL_BASE_API + "/getModultermine", {
      headers,
      params: {modulId}})
  }
}
