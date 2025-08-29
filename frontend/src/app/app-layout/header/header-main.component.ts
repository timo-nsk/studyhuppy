import {Component, Input, inject} from '@angular/core';
import {NgIf, NgOptimizedImage} from '@angular/common';
import {AuthApiService} from '../../auth-service/auth.service';
import {Router, RouterLink} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import  { LoginStatusService } from '../../auth-service/login-service/login-status.service'
import {AvatarComponent} from '../../user-profile/avatar/avatar.component';

@Component({
  selector: 'app-header-main',
  imports: [NgIf, RouterLink, AvatarComponent],
  templateUrl: './header-main.component.html',
  standalone: true,
  styleUrls: ['./header-main.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss']
})
export class HeaderMainComponent {
  loginStatusService = inject(LoginStatusService)
  authService = inject(AuthApiService)
  router = inject(Router)
  snackbar = inject(MatSnackBar)

  logout() : void {
    this.authService.logoff()
    this.loginStatusService.logout()
    this.router.navigateByUrl("login")
    this.snackbar.open("Sie wurden erfolgreich abgemeldet", "dismiss", {
      duration: 4000,
      verticalPosition: 'bottom',
      horizontalPosition: 'right'
    })
  }

}
