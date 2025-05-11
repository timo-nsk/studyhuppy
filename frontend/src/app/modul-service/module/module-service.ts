import {inject, Injectable} from '@angular/core';
import { Modul} from './modul';
import { HttpClient } from '@angular/common/http';
import {Observable } from 'rxjs';
import {HeaderService} from '../../header.service';

@Injectable({
  providedIn: 'root' // macht den Service global verfügbar
})
export class ModuleService {
  private MODUL_BASE_API = 'http://localhost:9080/api';
  headerService = inject(HeaderService)

  constructor(private http: HttpClient) {}

  resetTimer(fachId: string): void {
    this.http.put(this.MODUL_BASE_API + '/reset', fachId, {
      headers: { 'Content-Type': 'application/json' }
    }).subscribe({
      next: () => console.log('Request erfolgreich gesendet'),
      error: err => console.error('Fehler beim Senden:', err)
    });
  }

  getActiveModuleByUsername(): Observable<Modul[]> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<Modul[]>(this.MODUL_BASE_API + '/get-active-modules', {headers});
  }

  getAllModulesByUsername(): Observable<Modul[]> {
    const headers = this.headerService.createAuthHeader()
    return this.http.get<Modul[]>(this.MODUL_BASE_API + '/get-all-by-username', { headers });
  }

  async getSeconds(fachId: string): Promise<number> {
    try {
      const response = await fetch(this.MODUL_BASE_API + '/get-seconds', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'X-XSRF-TOKEN': this.getCsrfToken() || '',
        },
        body: JSON.stringify({ fachId: fachId })
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      return parseInt(await response.text());
    } catch (error) {
      console.error('Fehler beim Abrufen der Sekunden:', error);
      return 0
    }
  }

  // TODO: refactor later
  async postNewSeconds(fachId: string, sessionSecondsLearned: number): Promise<void> {
    const element = document.getElementById(fachId);
    if (!element || !element.dataset['value']) return;

    const seconds = parseInt(element.dataset['value'], 10);
    if (isNaN(seconds)) return;

    const payload = {
      fachId: fachId,
      secondsLearned: seconds,
      secondsLearnedThisSession: sessionSecondsLearned
    };

    try {
      const response = await this.doPost(payload, this.MODUL_BASE_API + '/update');

      if (response.ok) {
        // Daten erfolgreich gesendet
      } else {
        // Fehler beim Senden der Daten
      }
    } catch (error) {
      // Fehler beim Abrufen der API
    }
  }

  postFormData(formData : any) : void {
    this.http.post(this.MODUL_BASE_API + '/new-modul', formData)
  }

  async doPost(payload: unknown, api: string): Promise<Response> {
    return await fetch(api, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-XSRF-TOKEN': this.getCsrfToken() || ''
      },
      body: JSON.stringify(payload)
    });
  }

  getCsrfToken(): string {
    const row = document.cookie
      .split('; ')
      .find((cookie) => cookie.startsWith('XSRF-TOKEN='));

    if (!row) return "";

    return row.split('=')[1];
  }

  deleteModul(fachId: string) : Observable<void> {
    return this.http.delete<void>(this.MODUL_BASE_API + '/delete?fachId=' + fachId)
  }

  deactivateModul(fachId: string) : Observable<void> {
    return this.http.put<void>(this.MODUL_BASE_API + '/deactivate', fachId)
  }

  activateModul(fachId: string) : Observable<void> {
    return this.http.put<void>(this.MODUL_BASE_API + '/activate', fachId)
  }

  sendAddTimeData(data: any) {
    this.http.post(this.MODUL_BASE_API + '/add-time', data)
  }

  sendKlausurDateData(data: any) {
    this.http.post(this.MODUL_BASE_API + '/add-klausur-date', data)
  }
}
