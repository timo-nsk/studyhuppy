import {HttpClient, HttpResponse} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: "root"
})
export class AuthApiService {

  BASE_API_URL= "https://localhost:9084"

  router : Router = inject(Router)
  http : HttpClient = inject(HttpClient)
  snackbar : MatSnackBar = inject(MatSnackBar)

  login(data : any) : Observable<string>  {
    return this.http.post(this.BASE_API_URL + "/login", data, { responseType: 'text' })
  }

  logoff() : void {
    localStorage.removeItem('auth_token')
  }

  register(data: any) {
    this.http.post(this.BASE_API_URL + "/register", data).subscribe((r:any)=> {
      if(r.success) {
        console.log("registering complete")
        this.router.navigateByUrl("module")
      } else {
        console.log("internal server error")
        this.router.navigateByUrl("register")
      }
    })
  }

  pwReset(data: any): Observable<HttpResponse<any>> {
    return this.http.post(this.BASE_API_URL + "/password-reset", data, { observe: 'response' });
  }
}
