import {Component, inject} from '@angular/core';
import {HeaderAuthComponent} from '../../app-layout/header-auth/header-auth.component';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {AuthApiService} from '../auth.service';

@Component({
  selector: 'app-pw-service',
  imports: [
    HeaderAuthComponent,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './pw-service.component.html',
  standalone: true,
  styleUrls: ['./pw-service.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss']
})
export class PwServiceComponent {

  authService = inject(AuthApiService)

  pwResetForm : FormGroup = new FormGroup({
    mail: new FormControl("", [Validators.required, Validators.email])
  })

  sendPwResetRequest() {
    console.log("try password reset")
    const data = this.pwResetForm.value
    console.log(data)
    this.authService.pwReset(data)
  }
}
