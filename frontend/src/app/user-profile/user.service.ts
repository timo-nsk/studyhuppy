import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, Subscription} from 'rxjs';
import {User} from './user';

@Injectable({
  providedIn: "root"
})
export class UserApiService {
  BASE_API_URL : string = "http://localhost:9084/api/v1"

  http = inject(HttpClient)

  getUserData(): Observable<User> {
    const authToken: string | null = localStorage.getItem("auth_token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${authToken}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<User>(this.BASE_API_URL + "/get-user-data", { headers });
  }

}
