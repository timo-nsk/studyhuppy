import {Component, inject, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {UserApiService} from '../user.service';

@Component({
  selector: 'app-avatar',
  imports: [RouterLink],
  templateUrl: './avatar.component.html',
  standalone: true,
  styleUrl: './avatar.component.scss'
})
export class AvatarComponent implements OnInit{
  userService  = inject(UserApiService)
  userData : any;
  profilbildUrl: any;

  ngOnInit(): void {
    this.getUserData()
  }

  getUserData() {
    this.userService.getUserData().subscribe({
      next: (data) => {
        this.userData = data;
        console.log(this.userData)
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
}
