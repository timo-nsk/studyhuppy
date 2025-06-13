import {inject, Injectable} from '@angular/core';
import {LoggingService} from '../logging.service';
import {HttpClient} from '@angular/common/http';
import {HeaderService} from '../header.service';
import {Observable} from 'rxjs';
import { environment } from '../../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class MindmapApiService {
  BASE_API_URL = environment.mindmapApiUrl
  log = new LoggingService("MindmapApiService", "mindmap-service");
  http = inject(HttpClient);
  headerService = inject(HeaderService)

  getAllMindmapsByUsername() : Observable<any> {
    const header = this.headerService.createAuthHeader();
    return this.http.get(`${this.BASE_API_URL}/get-all-mindmaps-by-username`, { headers: header });
  }

  getMindmapByModulId(modulId: string) : Observable<any> {
    const header = this.headerService.createAuthHeader();
    return this.http.get<any>(`${this.BASE_API_URL}/get-mindmap-by-modulid`, {
      headers: header,
      params: {modulId: modulId}
    })

  }

  postNewNode(data: any): Observable<any> {
    const header = this.headerService.createAuthHeader();
    return this.http.post<any>(`${this.BASE_API_URL}/create-new-node`, data, { headers: header });

  }

  postNewMindmap(data : any): Observable<any> {

    const headers = this.headerService.createAuthHeader()
    return this.http.post<any>(this.BASE_API_URL + '/create-mindmap', data, {headers})
  }

  getMindmapsGroupedByModule() : Observable<any>{
    const header = this.headerService.createAuthHeader();
    return this.http.get<any>(`${this.BASE_API_URL}/get-all-mindmaps-groupedby-module`, { headers: header });
  }
}
