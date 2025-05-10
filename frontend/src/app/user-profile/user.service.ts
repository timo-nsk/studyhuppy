import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, Subscription} from 'rxjs';
import {User} from './user';
import {HeaderAuthComponent} from '../app-layout/header-auth/header-auth.component';
import {HeaderService} from '../header.service';

@Injectable({
  providedIn: "root"
})
export class UserApiService {
  BASE_API_URL : string = "http://localhost:9084/api/v1"

  http = inject(HttpClient)
  headerService = inject(HeaderService)

  getUserData(): Observable<User> {
    const authToken: string | null = localStorage.getItem("auth_token");

    const headers = this.headerService.createAuthHeader()

    return this.http.get<User>(this.BASE_API_URL + "/get-user-data", { headers });
  }

}
