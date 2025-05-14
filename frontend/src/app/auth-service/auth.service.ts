import {HttpClient, HttpResponse} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: "root"
})
export class AuthApiService {

  router : Router = inject(Router)
  http : HttpClient = inject(HttpClient)
  snackbar : MatSnackBar = inject(MatSnackBar)

  login(data : any) {
    console.log("trying login")
    this.http.post("http://localhost:9084/login", data).subscribe((r:any)=> {
      if(r.validated == false) {
        console.log("error valdiating credentials")
      } else {
        localStorage.setItem("auth_token", r.validated)
        console.log("success valdiating credentials")
        this.router.navigateByUrl("module")
      }
    })
  }

  logoff() : void {
    localStorage.removeItem('auth_token')
  }

  register(data: any) {
    this.http.post("http://localhost:9084/register", data).subscribe((r:any)=> {
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
    return this.http.post("http://localhost:9084/password-reset", data, { observe: 'response' });
  }
}
