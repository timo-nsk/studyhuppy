import {HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {LoggingService} from './logging.service';
import {jwtDecode} from 'jwt-decode';

@Injectable({
  providedIn: "root"
})
export class HeaderService {
  log = new LoggingService("HeaderService", "app")

  createAuthHeader() : HttpHeaders {
    let token = localStorage.getItem("auth_token") ?? ''
    const decoded = jwtDecode(token)
    const username = decoded.sub ?? 'none'
    this.log.debug(`Created header obj for user '${username}'`)
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }
}
