import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoggingService} from '../../../logging.service';
import {AuthApiService} from '../../../auth-service/auth.service';
import {NgIf} from '@angular/common';
import {SnackbarService} from '../../../snackbar.service';

@Component({
  selector: 'app-kontakt',
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './kontakt.component.html',
  standalone: true,
  styleUrls: ['./kontakt.component.scss', '../../../forms.scss', '../../../general.scss']
})
export class KontaktComponent {
  log = new LoggingService("KontaktComponent", "footer-compnent")
  sb = inject(SnackbarService)
  service = inject(AuthApiService)

  kontaktForm : FormGroup = new FormGroup({
    betreff: new FormControl("", Validators.required),
    nachricht: new FormControl("", [Validators.required, Validators.maxLength(2000)])
  })

  sendMessage() {
    if(this.kontaktForm.valid) {
      this.log.debug("form data VALID")
      const data = this.kontaktForm.value
      this.service.sendKontaktMessage(data).subscribe({
        next: () => {
          this.sb.openInfo("Deine Nachricht wurde erfolgreich gesendet. Vielen Dank!")
        },
        error: err => {
          this.sb.openInfo("Deine Nachricht konnte nicht versendet werden.")
          this.log.error("error sending kontakt message. Reason:")
          console.log(err)
        }
      })
    } else {
      this.kontaktForm.markAsTouched()
      this.log.debug("form data VALID")
    }
  }
}
