import {Component, inject, OnInit} from '@angular/core';
import {CommonModule, NgIf} from '@angular/common';
import {UserApiService} from './user.service';
import {User} from './user';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule, ReactiveFormsModule, NgIf, MatSnackBarModule],
  templateUrl: './user-profile.component.html',
  standalone: true,
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent implements OnInit{

  userData : any;

  userService  = inject(UserApiService)
  snackbar : MatSnackBar = inject(MatSnackBar)

  showEmailChangeForm : boolean = false
  showPassChangeForm : boolean = false

  changeMailSuccess = false
  changeMailFail = false
  changePassSuccess = false
  changePassFail = false

  emailChangeForm : FormGroup = new FormGroup({
    userId: new FormControl(null, Validators.required),
    newMail: new FormControl(null, [Validators.required, Validators.email])
  })

  changePassForm : FormGroup = new FormGroup({
    userId: new FormControl(null, Validators.required),
    oldPw: new FormControl(null, [Validators.required, Validators.min(8)]),
    newPw: new FormControl(null, [Validators.required, Validators.min(8)])
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
    this.emailChangeForm.patchValue({ userId: this.userData.userId})
    const data = this.emailChangeForm.value
    this.userService.putNewEmail(data).subscribe({
      next: (response) => {
        console.log("changed mail")
        this.changeMailFail = false
        this.snackbar.open("E-Mail-Adresse erfolgreich geändert!", "close", {
          duration: 4000
        })
      },
      error: (error) => {
        console.log("could not change pw")
        this.changeMailFail = true
      }
    })
  }

  putNewPass() {
    this.changePassForm.patchValue({ userId: this.userData.userId})
    const data = this.changePassForm.value

    this.userService.putNewPassword(data).subscribe({
      next: (response) => {
        console.log("changed password success")
        this.changePassFail = false;
        this.snackbar.open("Passwort erfolgreich geändert!", "close", {
          duration: 4000
        })
      },
      error: (error) => {
        console.log("could not change password")
        this.changePassFail = true;
      }
    })
  }

  togglePasswordChangeForm() {
    this.showPassChangeForm = !this.showPassChangeForm
  }
}
