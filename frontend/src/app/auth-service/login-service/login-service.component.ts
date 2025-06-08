import {Component, EventEmitter, Output, inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthApiService} from '../auth.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common';
import { LoggingService } from '../../logging.service';
import { LoginStatusService } from './login-status.service'
import {SnackbarService} from '../../snackbar.service';
import {jwtDecode} from 'jwt-decode';

@Component({
  selector: 'app-login-service',
  imports: [ReactiveFormsModule, RouterLink, NgIf
  ],
  templateUrl: './login-service.component.html',
  standalone: true,
  styleUrls: ['./login-service.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss', '../../forms.scss']
})
export class LoginServiceComponent {
  loginStatusService = inject(LoginStatusService)
  log : LoggingService = new LoggingService("LoginServiceComponent", "auth-service")
  router : Router = inject(Router)
  http : HttpClient = inject(HttpClient)
  authService : AuthApiService = inject(AuthApiService)
  sb = inject(SnackbarService)

  userNotExists : boolean = false
  badCredentials : boolean = false

  loginForm : FormGroup = new FormGroup({
    username : new FormControl("", Validators.required),
    password : new FormControl("", Validators.required)
  })

  submitLogin() : void {

    if(this.loginForm.valid) {
      this.log.debug("form data VALID")
      const data = this.loginForm.value
      this.authService.login(data).subscribe({
        next: (token: string) => {
          localStorage.setItem("auth_token", token);
          this.loginStatusService.login()
          this.router.navigateByUrl("module");
          this.log.debug(`user authentication SUCCESS. created auth_token: ${token} and saved in localStorage`)
          let decode = jwtDecode(token)
          let username = decode.sub
          this.sb.openInfo(`Erfolgreich angemeldet. Willkommen ${username}!`)
        },
        error: (err) => {
          this.loginStatusService.logout()
          if(err.status == 404) {
            this.log.error("user not found")
            this.userNotExists = true
            this.badCredentials = false
          } else if (err.status == 401) {
            this.log.error("error validating credentials")
            this.badCredentials = true
            this.userNotExists = false
          } else {
            this.log.error(`error occured while login try.`)
            console.log(err)
          }
        }
      });
    } else {
      this.log.debug("form data INVALID")
    }
  }
}
