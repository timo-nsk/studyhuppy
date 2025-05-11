import {Component, inject, OnInit} from '@angular/core';
import {CommonModule, NgIf} from '@angular/common';
import {UserApiService} from './user.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule, ReactiveFormsModule, NgIf, MatSnackBarModule],
  templateUrl: './user-profile.component.html',
  standalone: true,
  styleUrls: ['./user-profile.component.scss', '../general.scss', '../button.scss', '../color.scss']
})
export class UserProfileComponent implements OnInit{

  userData : any;

  userService  = inject(UserApiService)
  snackbar : MatSnackBar = inject(MatSnackBar)

  showEmailChangeForm : boolean = false
  showPassChangeForm : boolean = false
  showAssertDeletionBox : boolean = false

  changePassFail = false

  emailChangeForm : FormGroup = new FormGroup({
    userId: new FormControl(""),
    newMail: new FormControl(null, [Validators.required, Validators.email])
  })

  changePassForm : FormGroup = new FormGroup({
    userId: new FormControl(null, Validators.required),
    oldPw: new FormControl(null, [Validators.required, Validators.minLength(8)]),
    newPw: new FormControl(null, [Validators.required, Validators.minLength(8)])
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
    if(this.emailChangeForm.invalid) {
      this.emailChangeForm.markAsTouched()
      return
    }

    this.emailChangeForm.patchValue({ userId: this.userData.userId})
    const data = this.emailChangeForm.value
    this.userService.putNewEmail(data).subscribe({
      next: (response) => {
        this.snackbar.open("E-Mail-Adresse erfolgreich geändert!", "close", {
          duration: 4000
        })
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

  toggleAssertDeletionBox() {
    this.showAssertDeletionBox = true;
  }

  deleteAccount() {
    const data = this.userData.userId
    this.userService.deleteAccount(data)
  }
}
