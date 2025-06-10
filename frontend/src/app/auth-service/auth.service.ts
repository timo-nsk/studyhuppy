import {HttpClient, HttpResponse} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import { environment } from '../../environments/environment'
import {SnackbarService} from '../snackbar.service';
import {LoggingService} from '../logging.service';
import {HeaderService} from '../header.service';

@Injectable({
  providedIn: "root"
})
export class AuthApiService {
  BASE_API_URL= environment.authApiUrl

  log = new LoggingService("AuthApiService", "auth-service")

  router : Router = inject(Router)
  http : HttpClient = inject(HttpClient)
  sb= inject(SnackbarService)
  headerService = inject(HeaderService)

  login(data : any) : Observable<string>  {
    this.log.debug("try logging in...")
    return this.http.post(this.BASE_API_URL + "/login", data, { responseType: 'text' })
  }

  logoff() : void {
    localStorage.removeItem('auth_token')
    this.log.debug("logoff successfull")
    this.sb.openInfo("Sie wurden abgemeldet")
  }

  register(data: any) : Observable<void>{
     return this.http.post<void>(this.BASE_API_URL + "/register", data)
  }

  pwReset(data: any): Observable<HttpResponse<any>> {
    this.log.debug("try reseting password in...")
    return this.http.post(this.BASE_API_URL + "/password-reset", data, { observe: 'response' });
  }

  getAllUsers(): Observable<any> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<any>(this.BASE_API_URL + "/get-all-users", { headers})
  }
}
