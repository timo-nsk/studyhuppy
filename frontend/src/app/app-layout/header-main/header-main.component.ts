import {Component, inject} from '@angular/core';
import {AuthApiService} from '../../auth-service/auth.service';
import {Router} from '@angular/router';

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

  logout() : void {
    this.authService.logoff()
    this.router.navigateByUrl("login")
  }

}
