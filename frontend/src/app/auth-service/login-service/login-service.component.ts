import {Component, inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthApiService} from '../auth.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common';
import { HeaderAuthComponent} from '../../app-layout/header-auth/header-auth.component';
import {FooterComponent} from '../../app-layout/footer/footer.component'

@Component({
  selector: 'app-login-service',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    NgIf,
    HeaderAuthComponent,
    FooterComponent
  ],
  templateUrl: './login-service.component.html',
  standalone: true,
  styleUrls: ['./login-service.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss', '../../forms.scss']
})
export class LoginServiceComponent {
  router : Router = inject(Router)
  http : HttpClient = inject(HttpClient)
  authService : AuthApiService = inject(AuthApiService)

  userNotExists : boolean = false
  badCredentials : boolean = false

  loginForm : FormGroup = new FormGroup({
    username : new FormControl("", Validators.required),
    password : new FormControl("", Validators.required)
  })

  submitLogin() : void {
    const data = this.loginForm.value
    this.authService.login(data).subscribe({
      next: (token: string) => {
        localStorage.setItem("auth_token", token);
        //console.log("success validating credentials");
        this.router.navigateByUrl("module");
      },
      error: (err) => {

        if(err.status == 404) {
          //console.log("no user found")
          this.userNotExists = true
          this.badCredentials = false
        } else if (err.status == 401) {
          //console.log("error validating credentials");
          this.badCredentials = true
          this.userNotExists = false
        }
      }
    });
  }
}
