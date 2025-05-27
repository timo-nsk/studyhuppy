import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authenticationGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem("auth_token");

  if (token != null) {
    return true;
  } else {
    return router.parseUrl('/login');
  }
};
