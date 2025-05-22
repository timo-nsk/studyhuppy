import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {HeaderService} from '../header.service';
import {Stapel, UpdateInfo} from './domain';

@Injectable({
  providedIn: 'root'
})
export class KarteiApiService {
  BASE_API_URL : string = "http://localhost:9081/api"

  http = inject(HttpClient)
  headerService = inject(HeaderService)

  checkSetsAvailable() : Observable<boolean>{
    const headers = this.headerService.createAuthHeader()
    return this.http.get<boolean>(this.BASE_API_URL + "/sets-available", {headers})
  }

  getAllStapelByUsername() : Observable<any>{
    const headers = this.headerService.createAuthHeader()
    return this.http.get<any>(this.BASE_API_URL + "/get-all-stapel-by-username", {headers})
  }

  getStapelByFachId(fachId: string | null) : Observable<Stapel> {
    const headers = this.headerService.createAuthHeader()
    return this.http.post<Stapel>( this.BASE_API_URL + "/get-stapel-by-fachid",fachId,  {headers})
  }

  updateKarte(data : UpdateInfo) : Observable<any> {
    console.log(data)
    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>( this.BASE_API_URL + '/update-karteikarte' ,data ,  {headers})
  }

  postNewStapel(data : any) : Observable<any> {
    console.log(data)
    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>(this.BASE_API_URL + '/create-stapel', data, {headers})
  }

  sendNeuekarteData(data: any, typ : string) : Observable<any> {
    const headers = this.headerService.createAuthHeader()
    if(typ == 'n')
      return this.http.post<any>(this.BASE_API_URL + '/add-neue-karte-normal', data, {headers})
    else if (typ == 'c')
      console.log("sendneuekartedata ping")
      return this.http.post<any>(this.BASE_API_URL + '/add-neue-karte-choice', data, {headers})
  }

  deleteKarte(stapelId: string | null, karteId: string) : Observable<any> {
    const headers = this.headerService.createAuthHeader()
    return this.http.delete<any>(this.BASE_API_URL + "/delete-karte", {
      headers,
      body: {
        "stapelId" : stapelId,
        "karteId" : karteId
      }
    })
  }

  putEditedData(data: any) : Observable<any> {
    const headers = this.headerService.createAuthHeader()
    return this.http.put<any>(this.BASE_API_URL + "/edit-karte-normal", data, {headers})
  }

  putChoiceKarteEditedData(data: any) : Observable<any> {
    const headers = this.headerService.createAuthHeader()
    return this.http.put<any>(this.BASE_API_URL + "/edit-karte-choice", data, {headers})
  }

  removeAntwortFromKarte(stapelId: string | null, karteId : string | undefined, antwortIndex: number) : Observable<any> {
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
}
