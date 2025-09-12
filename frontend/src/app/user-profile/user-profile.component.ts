import {Component, inject, OnInit} from '@angular/core';
import {CommonModule, NgIf, NgOptimizedImage} from '@angular/common';
import {UserApiService} from './user.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSlideToggle} from '@angular/material/slide-toggle';
import {SnackbarService} from '../snackbar.service';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule, ReactiveFormsModule, NgIf, MatSnackBarModule, MatSlideToggle],
  templateUrl: './user-profile.component.html',
  standalone: true,
  styleUrls: ['./user-profile.component.scss', '../general.scss', '../button.scss', '../color.scss']
})
export class UserProfileComponent implements OnInit{

  userData : any;

  userService  = inject(UserApiService)
  snackbarService = inject(SnackbarService)

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
  newMailAlreadyExists: boolean = false
  internalServerError : boolean = false

  profilbildUrl : any;

  ngOnInit(): void {
    this.getUserData()
  }

  getUserData() {
    this.userService.getUserData().subscribe({
      next: (data) => {
        this.userData = data;
        if(this.userData.profilbildPath != 'none') {
          this.getProfilbild()
        } else {
          this.profilbildUrl = 'assets/default-avatar.png'
        }
      },
      error: (err) => {
        console.error('Fehler beim Laden:', err);
      }
    });
  }

  getProfilbild() {
    this.userService.getProfilbild(this.userData).subscribe( {
      next: blob => {
        console.log(blob)
        const reader = new FileReader();
        reader.onload = () => this.profilbildUrl = reader.result as string;
        reader.readAsDataURL(blob);
      },
      error: err => {
        console.log(err)
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
        this.getUserData()
        this.emailChangeForm.reset()
        this.snackbarService.openSuccess("E-Mail-Adresse erfolgreich geändert!")
      },
      error: (err) => {
        if(err.status == 409) {
          this.newMailAlreadyExists = true
        } else {
          this.internalServerError = true
        }
      }
    })
  }

  putNewPass() {
    this.changePassForm.patchValue({ userId: this.userData.userId})
    const data = this.changePassForm.value

    this.userService.putNewPassword(data).subscribe({
      next: (response) => {
        this.getUserData()
        this.changePassForm.reset()
        console.log("changed password success")
        this.changePassFail = false;
        this.snackbarService.openSuccess("Passwort erfolgreich geändert!");
      },
      error: (err) => {
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

  //todo FIX: IST: nach löschung bleibt user auf seite. SOLL: nach löschung weiterleitung auf startseite
  deleteAccount() {
    const data = this.userData.userId
    this.userService.deleteAccount(data)
  }

  selectProfilbild(event: Event): void {
    const input = event.target as HTMLInputElement | null;
    if (!input?.files || input.files.length === 0) {
      return;
    }

    const file = input.files[0];
    const reader = new FileReader();
    reader.onload = () => {
      this.profilbildUrl = reader.result as string;

      this.userService.postNewProfilbild(this.profilbildUrl).subscribe({
        next: () => {
          this.snackbarService.openSuccess("Profilbild erfolgreich geändert!");
        },
        error: (status) => {
          if (status.value === 400) {
            this.snackbarService.openError("Fehler beim Ändern des Profilbildes!")
          }
        }
      })
    };
    reader.readAsDataURL(file);
  }


}
