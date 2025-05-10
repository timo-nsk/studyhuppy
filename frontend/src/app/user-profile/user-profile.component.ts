import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserApiService} from './user.service';
import {User} from './user';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  standalone: true,
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent implements OnInit{

  userData : any;

  userService  = inject(UserApiService)

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
}
