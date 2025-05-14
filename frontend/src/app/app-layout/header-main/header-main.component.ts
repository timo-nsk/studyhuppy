import {Component, inject} from '@angular/core';
import {AuthApiService} from '../../auth-service/auth.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-header-main',
  imports: [],
  templateUrl: './header-main.component.html',
  standalone: true,
  styleUrls: ['./header-main.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss']
})
export class HeaderMainComponent {

  authService = inject(AuthApiService)
  router = inject(Router)
  snackbar = inject(MatSnackBar)

  logout() : void {
    this.authService.logoff()
    this.router.navigateByUrl("login")
    this.snackbar.open("Sie wurden erfolgreich abgemeldet", "dismiss", {
      duration: 4000,
      verticalPosition: 'bottom',
      horizontalPosition: 'right'
    })
  }

}
