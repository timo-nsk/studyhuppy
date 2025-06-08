import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {HeaderService} from '../header.service';
import {Stapel, UpdateInfo} from './domain';
import { environment } from '../../environments/environment'
import {LoggingService} from '../logging.service';

@Injectable({
  providedIn: 'root'
})
export class KarteiApiService {
  BASE_API_URL : string = environment.apiUrl
  log = new LoggingService("KarteiApiService", "kartei-service")
  http = inject(HttpClient)
  headerService = inject(HeaderService)

  checkSetsAvailable() : Observable<boolean>{
    this.log.debug("try checking sets available...")
    const headers = this.headerService.createAuthHeader()
    return this.http.get<boolean>(this.BASE_API_URL + "/sets-available", {headers})
  }

  getAllStapelByUsername() : Observable<any>{
    this.log.debug("try getting all stapel by username...")
    const headers = this.headerService.createAuthHeader()
    return this.http.get<any>(this.BASE_API_URL + "/get-all-stapel-by-username", {headers})
  }

  getStapelByFachId(fachId: string | null | undefined) : Observable<Stapel> {
    this.log.debug("try getting stapel by fachid...")
    const headers = this.headerService.createAuthHeader()
    return this.http.post<Stapel>( this.BASE_API_URL + "/get-stapel-with-faellige-karteikarten-by-fachid",fachId,  {headers})
  }

  getStapelWithAllKarteikartenByFachId(fachId: string | null | undefined) : Observable<Stapel> {
    this.log.debug("try getting stapel with all karteikarten by fachid...")
    const headers = this.headerService.createAuthHeader()
    return this.http.post<Stapel>( this.BASE_API_URL + "/get-stapel-with-all-karteikarten-by-fachid",fachId,  {headers})
  }

  updateKarte(data : UpdateInfo) : Observable<any> {
    this.log.debug("try updating karteikarte...")
    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>( this.BASE_API_URL + '/update-karteikarte' ,data ,  {headers})
  }

  postNewStapel(data : any) : Observable<any> {
    this.log.debug("try creating new stapel...")
    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>(this.BASE_API_URL + '/create-stapel', data, {headers})
  }

  sendNeuekarteData(data: any, typ : string) : Observable<any> {
    this.log.debug("try adding new karteikarte to stapel...")
    const headers = this.headerService.createAuthHeader()
    if(typ == 'n')
      return this.http.post<any>(this.BASE_API_URL + '/add-neue-karte-normal', data, {headers})
    else if (typ == 'c')
      return this.http.post<any>(this.BASE_API_URL + '/add-neue-karte-choice', data, {headers})
    return new Observable()
  }

  deleteKarte(stapelId: string | null, karteId: string) : Observable<any> {
    this.log.debug("try deleting karteikarte from stapel...")
    const headers = this.headerService.createAuthHeader()
    return this.http.delete<any>(this.BASE_API_URL + "/delete-karte", {
      headers,
      body: {
        "stapelId" : stapelId,
        "karteId" : karteId
      }
    })
  }

  putNormalKarteEditedData(data: any) : Observable<any> {
    this.log.debug("try editing normal karteikarte...")
    const headers = this.headerService.createAuthHeader()
    return this.http.put<any>(this.BASE_API_URL + "/edit-karte-normal", data, {headers})
  }

  putChoiceKarteEditedData(data: any) : Observable<any> {
    this.log.debug("try editing choice karteikarte...")
    const headers = this.headerService.createAuthHeader()
    return this.http.put<any>(this.BASE_API_URL + "/edit-karte-choice", data, {headers})
  }

  removeAntwortFromKarte(stapelId: string | null, karteId : string | undefined, antwortIndex: number) : Observable<any> {
    this.log.debug("try removing antwort from choice karteikarte...")
    const headers = this.headerService.createAuthHeader()
    return this.http.delete<any>(this.BASE_API_URL + '/remove-antwort-from-karte', {
      headers,
        body: {
            "stapelId": stapelId,
            "karteId": karteId,
            "antwortIndex": antwortIndex
        }
    })
  }

  postKartenFile(formData: FormData) : Observable<string> {
    this.log.debug("try sending karteikarten file...")
    let token = localStorage.getItem("auth_token")
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    return this.http.post<string>(this.BASE_API_URL + '/import-karten', formData, { headers })
  }
}
