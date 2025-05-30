import {Component, inject} from '@angular/core';
import {RouterLink} from '@angular/router';
import {AuthApiService} from '../auth.service';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {HeaderAuthComponent} from '../../app-layout/header-auth/header-auth.component';

@Component({
  selector: 'app-register-service',
  imports: [
    RouterLink,
    ReactiveFormsModule,
    HeaderAuthComponent
  ],
  templateUrl: './register-service.component.html',
  standalone: true,
  styleUrls: ['./register-service.component.scss', '../../general.scss', '../../forms.scss']
})
export class RegisterServiceComponent {

  registerForm : FormGroup = new FormGroup({
    mail: new FormControl(""),
    username: new FormControl(""),
    password: new FormControl(""),
    semester: new FormControl(""),
    notificationSubscription: new FormControl(""),
    acceptedAgb: new FormControl("")
  })

  authService : AuthApiService = inject(AuthApiService)

  submitRegister() {
    let data = this.registerForm.value
    this.authService.register(data)
    console.log(data)
  }

}
