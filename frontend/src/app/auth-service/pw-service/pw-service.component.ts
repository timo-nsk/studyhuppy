import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {AuthApiService} from '../auth.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-pw-service',
  imports: [
    ReactiveFormsModule,
    NgIf,
    RouterLink
  ],
  templateUrl: './pw-service.component.html',
  standalone: true,
  styleUrls: ['./pw-service.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss']
})
export class PwServiceComponent {

  authService = inject(AuthApiService)

  emailNotFound : boolean = false;
  emailFound : boolean = false;

  pwResetForm : FormGroup = new FormGroup({
    mail: new FormControl("", [Validators.required, Validators.email])
  })

  sendPwResetRequest() {
    const data = this.pwResetForm.value

    if (this.pwResetForm.invalid) {
      this.pwResetForm.markAllAsTouched();
      return;
    }
    console.log("hiiiii")

    this.authService.pwReset(data).subscribe({
      next: (response) => {
        this.emailFound = true;
        this.emailNotFound = false;
      },
      error: (error) => {
        if (error.status === 404) {
          this.emailNotFound = true;
          this.emailFound = false;
        } else {
          console.error("Ein Fehler ist aufgetreten:", error);
        }
      }
    });
  }
}
