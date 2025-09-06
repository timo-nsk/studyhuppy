import {Component, inject} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {AuthApiService} from '../auth.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoggingService} from '../../logging.service';
import {SnackbarService} from '../../snackbar.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-register-service',
  imports: [
    RouterLink,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './register-service.component.html',
  standalone: true,
  styleUrls: ['./register-service.component.scss', '../../general.scss', '../../forms.scss', '../../color.scss']
})
export class RegisterServiceComponent {
  log = new LoggingService("RegisterServiceComponent", "auth-service")
  authService : AuthApiService = inject(AuthApiService)
  sb = inject(SnackbarService)
  router = inject(Router)
  userAlreadyExists = false

  registerForm : FormGroup = new FormGroup({
    mail: new FormControl("", [Validators.required, Validators.email]),
    username: new FormControl("", [Validators.required, Validators.minLength(3), Validators.maxLength(30)]),
    password: new FormControl("", [Validators.required, Validators.minLength(8), Validators.maxLength(20)]),
    semester: new FormControl(""),
    notificationSubscription: new FormControl(""),
    acceptedAgb: new FormControl("", Validators.required)
  })

  submitRegister() {
    if(this.registerForm.valid) {
      this.log.debug("form data VALID")
      let data = this.registerForm.value
      this.authService.register(data).subscribe({
        next: () => {
          this.log.debug("person registration successfull")
          this.router.navigateByUrl("/login")
            .then(() => {
              this.sb.openInfo("Ihr Benutzerkonto wurde erfolgreich angelegt. Sie können sich nun anmelden.")
            })
        },
        error: err => {
          if(err.status == 400) {
            this.userAlreadyExists = true
            this.log.error(`error during user registration. reason: ${err}`)
          }
        }
      })
    } else {
      this.log.debug("form data INVALID")
      this.registerForm.markAllAsTouched()
    }
  }
}
