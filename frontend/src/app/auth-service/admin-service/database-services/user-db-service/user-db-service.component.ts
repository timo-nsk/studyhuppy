import {Component, inject, OnInit} from '@angular/core';
import {AuthApiService} from '../../../auth.service';
import {LoggingService} from '../../../../logging.service';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-user-db-service',
  imports: [
    NgForOf
  ],
  templateUrl: './user-db-service.component.html',
  standalone: true,
  styleUrl: './user-db-service.component.scss'
})
export class UserDbServiceComponent implements OnInit{
  log = new LoggingService("UserDbServiceComponent", "admin-service")
  authService = inject(AuthApiService)

  users : any[] = []

  getUserData() {
    this.authService.getAllUsers().subscribe({
      next: data => {
        this.users = data
        this.log.info("got user data:")
        console.log(data)
      },
      error: err => {
        this.log.error("error fetching user data. Reason:")
        console.log(err)
      }
    })
  }

  ngOnInit(): void {
    this.getUserData()
  }

  deleteUser(userId: any) {
    console.log(userId)
    this.authService.deleteUserById(userId).subscribe({
      next: () => {
        this.log.info("user successfully deleted")
        this.getUserData()
      },
      error: err => {
        this.log.error("could not delete user from system. Reason:")
        console.log(err)
      }
    })
  }
}
