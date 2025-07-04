import {Component, inject} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {AuthApiService} from '../auth.service';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {LoggingService} from '../../logging.service';
import {SnackbarService} from '../../snackbar.service';

@Component({
  selector: 'app-register-service',
  imports: [
    RouterLink,
    ReactiveFormsModule
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

  //TODO: add validation, need to check if username is available in backend
  registerForm : FormGroup = new FormGroup({
    mail: new FormControl(""),
    username: new FormControl(""),
    password: new FormControl(""),
    semester: new FormControl(""),
    notificationSubscription: new FormControl(""),
    acceptedAgb: new FormControl("")
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
          this.log.error(`error during user registration. reason: ${err}`)
          this.sb.openError("Registrierung fehlgeschlagen. Versuchen Sie es später erneut.")
        }
      })
    } else {
      this.log.debug("form data INVALID")
    }
  }
}
