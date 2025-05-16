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

  getStapelByFachId(fachId : string) : Observable<Stapel> {
    const headers = this.headerService.createAuthHeader()
    return this.http.post<Stapel>( this.BASE_API_URL + "/get-stapel-by-fachid",fachId,  {headers})
  }

  updateKarte(data : UpdateInfo) : void {
    console.log(data)
    const headers = this.headerService.createAuthHeader()
    this.http.post( "http://localhost:9081/update-karteikarte",data,  {headers}).subscribe()
  }

  postNewStapel(data : any) : Observable<any> {
    console.log(data)
    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>(this.BASE_API_URL + '/create-stapel', data, {headers})
  }
}
