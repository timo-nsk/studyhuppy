import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable, Subscription} from 'rxjs';
import {User} from './user';
import {HeaderAuthComponent} from '../app-layout/header-auth/header-auth.component';
import {HeaderService} from '../header.service';
import { environment } from '../../environments/environment'

@Injectable({
  providedIn: "root"
})
export class UserApiService {
  BASE_API_URL : string = environment.authServiceUrl

  http = inject(HttpClient)
  headerService = inject(HeaderService)

  getUserData(): Observable<User> {
    const headers = this.headerService.createAuthHeader()

    return this.http.get<User>(this.BASE_API_URL + "/get-user-data", { headers });
  }

  updateNotificationSubscription(notificationSubscription : boolean) {
    const headers = this.headerService.createAuthHeader()
    const payload = JSON.stringify({
      activate: notificationSubscription
    });

    return this.http.put<User>(this.BASE_API_URL + "/update-notification-subscription",payload, { headers })
      .subscribe();
  }

  putNewEmail(data: any): Observable<HttpResponse<any>> {
    const headers = this.headerService.createAuthHeader()
    return this.http.put<any>(this.BASE_API_URL + "/change-mail", data, { headers, observe: 'response' });
  }

  putNewPassword(data: any) : Observable<HttpResponse<any>> {
    const headers = this.headerService.createAuthHeader()
    return this.http.put<any>(this.BASE_API_URL + "/change-password", data, { headers, observe : 'response'});
  }

  deleteAccount(data: any) {
    const headers = this.headerService.createAuthHeader()

    this.http.request('DELETE', this.BASE_API_URL + '/delete-account', {
      headers,
      body: data
    }).subscribe({
      next: (response) => {
        console.log('Account deleted successfully', response);
      },
      error: (err) => {
        console.error('Error deleting account', err);
      }
    });
  }
}
