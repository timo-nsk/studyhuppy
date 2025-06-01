import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import {LoginStatusService} from '../auth-service/login-service/login-status.service';

export const authenticationGuard: CanActivateFn = (route, state) => {
  let loginStatusService = inject(LoginStatusService)
  const router = inject(Router);
  const token = localStorage.getItem("auth_token");

  if (token != null) {
    loginStatusService.login()
    return true;
  } else {
    loginStatusService.logout()
    return router.parseUrl('/login');
  }
};
