import {HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: "root"
})
export class HeaderService {

  createAuthHeader() : HttpHeaders {
    let token = localStorage.getItem("auth_token")
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }
}
