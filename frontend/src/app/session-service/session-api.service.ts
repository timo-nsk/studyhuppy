import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Session, SessionInfoDto} from './session-domain';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {HeaderService} from '../header.service';
import {LoggingService} from '../logging.service';

@Injectable({
  providedIn: 'root'
})
export class SessionApiService {
  private SESSION_BASE_API = environment.sessionApiUrl
  private headerService = inject(HeaderService)
  private http = inject(HttpClient)
  private log = new LoggingService("SessionApiService", "session-service")

  saveSession(session: Session): Observable<any> {
    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>(this.SESSION_BASE_API + '/create', session, {headers})

  }

  getSessions(): Observable<any> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<any>(this.SESSION_BASE_API + '/get-sessions', {headers})
  }

  deleteSession(fachId : string | undefined) : Observable<any>{
    const headers = this.headerService.createAuthHeader()
    return this.http.delete<any>(this.SESSION_BASE_API + "/delete-session", {
      headers,
      body: { fachId }
    })
  }

  getLernplanSessionData() {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<SessionInfoDto[]>(this.SESSION_BASE_API + '/get-lernplan-session-data', {headers})
  }

  getSession(sessionId : string) : Observable<any>{
    const headers = this.headerService.createAuthHeader()
    return this.http.get<any>(this.SESSION_BASE_API + '/get-session-by-id', {
      headers,
      params: {
        sessionId: sessionId
      }
    })
  }

  hasLernSessions(): Observable<any> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<any>(this.SESSION_BASE_API + '/has-lernsessions', {headers})
  }
}
