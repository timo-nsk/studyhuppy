import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthApiService} from '../auth.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {RouterLink, RouterOutlet} from '@angular/router';
import {NgIf} from '@angular/common';
import { HeaderAuthComponent} from '../../app-layout/header-auth/header-auth.component';

@Component({
  selector: 'app-login-service',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    NgIf,
    HeaderAuthComponent
  ],
  templateUrl: './login-service.component.html',
  standalone: true,
  styleUrls: ['./login-service.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss']
})
export class LoginServiceComponent {

  loginForm : FormGroup = new FormGroup({
    username : new FormControl("", Validators.required),
    password : new FormControl("", Validators.required)
  })

  constructor(private http : HttpClient, private authService : AuthApiService) {}

  submitLogin() : void {
    const data = this.loginForm.value
    this.authService.login(data)
  }
}
