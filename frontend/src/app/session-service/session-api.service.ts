import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Session, SessionBewertung, SessionInfoDto} from './session-domain';
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

  postCreatedSessionData(session: Session): Observable<any> {
    console.log("saveSession called with session: ", session)
    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>(this.SESSION_BASE_API + '/create', session, {headers})
  }

  postEditedSessionData(session: Session) {
    console.log("saveSession called with edited session: ", session)
    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>(this.SESSION_BASE_API + '/edited-session', session, {headers})
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

  sendSessionBewertung(sessionBewertung: SessionBewertung, abgebrochen : boolean) {
    const headers = this.headerService.createAuthHeader()
    const payload = {
      bewertung: sessionBewertung,
      abgebrochen: abgebrochen
    }
    return this.http.post<any>(
      this.SESSION_BASE_API + '/save-session-beendet-event',
      payload,
      { headers }
    );
  }

}
