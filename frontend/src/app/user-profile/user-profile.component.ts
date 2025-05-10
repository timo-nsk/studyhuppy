import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserApiService} from './user.service';
import {User} from './user';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-profile.component.html',
  standalone: true,
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent implements OnInit{

  userData : any;

  userService  = inject(UserApiService)

  showEmailChangeForm : boolean = false

  changeSuccess = false
  changeFail = false

  emailChangeForm : FormGroup = new FormGroup({
    newMail: new FormControl(null, [Validators.required, Validators.email])
  })

  ngOnInit(): void {
    this.getUserData()
  }

  getUserData() {
    this.userService.getUserData().subscribe({
      next: (data) => {
        this.userData = data;
        console.log(this.userData);
      },
      error: (err) => {
        console.error('Fehler beim Laden:', err);
      }
    });
  }

  toggleEmailSubscription() {
    console.log("vorher: " + this.userData.notificationSubscription)
    this.userData.notificationSubscription = !this.userData.notificationSubscription
    console.log("nacher: " + this.userData.notificationSubscription)
    this.userService.updateNotificationSubscription(this.userData.notificationSubscription)
  }

  toggleEmailChangeForm() {
    this.showEmailChangeForm = !this.showEmailChangeForm
  }

  putNewEmail() {
    const data = this.emailChangeForm.value
    this.userService.putNewEmail(data).subscribe({
      next: (response) => {
        console.log("changed mail")
        this.changeSuccess = true
      },
      error: (error) => {
        console.log("could not change pw")
        this.changeFail = true
      }
    })
  }
}
