import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {AuthApiService} from '../auth.service';
import {RouterLink} from '@angular/router';
import {LoggingService} from '../../logging.service';

@Component({
  selector: 'app-pw-service',
  imports: [ReactiveFormsModule, NgIf, RouterLink],
  templateUrl: './pw-service.component.html',
  standalone: true,
  styleUrls: ['./pw-service.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss']
})
export class PwServiceComponent {
  log = new LoggingService("PwServiceComponent", "auth-service")

  authService = inject(AuthApiService)

  emailNotFound : boolean = false;
  emailFound : boolean = false;

  pwResetForm : FormGroup = new FormGroup({
    mail: new FormControl("", [Validators.required, Validators.email])
  })

  sendPwResetRequest() {
    if (this.pwResetForm.valid) {
      this.log.debug("form data VALID")
      const data = this.pwResetForm.value
      this.authService.pwReset(data).subscribe({
        next: (response) => {
          this.emailFound = true;
          this.emailNotFound = false;
          this.log.debug("password succesfully reset")
        },
        error: (error) => {
          if (error.status === 404) {
            this.emailNotFound = true;
            this.emailFound = false;
            this.log.error(`error resetting password. reason: no email available`)
          } else {
            this.log.error(`error resetting password. reason: ${error}`)
          }
        }
      });
    } else {
      this.log.debug("form data INVALID")
      this.pwResetForm.markAllAsTouched();
    }
  }
}
