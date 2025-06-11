import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoggingService} from '../../../logging.service';
import {AuthApiService} from '../../../auth-service/auth.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-kontakt',
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './kontakt.component.html',
  standalone: true,
  styleUrl: './kontakt.component.scss'
})
export class KontaktComponent {
  log = new LoggingService("KontaktComponent", "footer-compnent")
  kontaktForm : FormGroup = new FormGroup({
    betreff: new FormControl("", Validators.required),
    nachricht: new FormControl("", [Validators.required, Validators.maxLength(2000)])
  })

  service = inject(AuthApiService)

  sendMessage() {
    if(this.kontaktForm.valid) {
      this.log.debug("form data VALID")
      const data = this.kontaktForm.value
      this.service.sendKontaktMessage(data).subscribe({
        next: () => {

        },
        error: err => {
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
