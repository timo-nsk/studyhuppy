import {HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: "root"
})
export class HeaderService {

  createAuthHeader() : HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("auth_token")}`,
      'Content-Type': 'application/json'
    });
  }
}
