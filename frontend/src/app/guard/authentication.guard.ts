import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import {LoginStatusService} from '../auth-service/login-service/login-status.service';
import {jwtDecode} from 'jwt-decode';
import {LoggingService} from '../logging.service';



export const authenticationGuard: CanActivateFn = (route, state) => {
  const log = new LoggingService("authenticationGuard", "auth-service")

  let loginStatusService = inject(LoginStatusService)
  const router = inject(Router);
  const token = localStorage.getItem("auth_token");


  if (token != null) {
    const decoded = jwtDecode(token)
    const username = decoded.sub ?? 'none'
    log.debug(`User '${username}' authentication successfull`)
    loginStatusService.login()
    return true;
  } else {
    loginStatusService.logout()
    return router.parseUrl('/login');
  }
};
