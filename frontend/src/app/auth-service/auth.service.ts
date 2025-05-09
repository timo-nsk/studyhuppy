import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {routes} from '../app.routes';

@Injectable({
  providedIn: "root"
})
export class AuthApiService {

  router : Router = inject(Router)
  http : HttpClient = inject(HttpClient)

  login(data : any) {
    console.log("trying login")
    this.http.post("http://localhost:9084/login/auth", data).subscribe((r:any)=> {
      if(r.validated == false) {
        console.log("error valdiating credentials")
      } else {
        localStorage.setItem("auth_token", r.validated)
        console.log("success valdiating credentials")
        this.router.navigateByUrl("module")
      }
    })
  }

  register(data: any) {
    console.log("trying register")
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
}
