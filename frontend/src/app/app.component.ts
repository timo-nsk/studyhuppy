import {Component} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet} from '@angular/router';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FooterComponent } from './app-layout/footer/footer.component';
import { HeaderMainComponent } from './app-layout/header/header-main.component';

@Component({
  selector: 'app-root',
  imports: [
    CommonModule,
    RouterOutlet,
    MatSnackBarModule,
    HeaderMainComponent,
    FooterComponent
  ],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrls: ['./app.component.scss', './general.scss', './app-layout/side-navbar.scss']
})
export class AppComponent {
  isLoggedIn : boolean = false

  receiveLoggedInEvent($event: boolean) {
    this.isLoggedIn = $event
  }
}
