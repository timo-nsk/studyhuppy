import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import * as jwt from 'jwt-decode';

export const authorityGuard: CanActivateFn = (route, state) => {
  const router: Router = inject(Router);
  const token : string = localStorage.getItem("auth_token") ?? "";

  try {
    const decoded: any = jwt.jwtDecode(token)
    const authorities = decoded.authorities || [];

    if (authorities.includes('ROLE_ADMIN')) {
      return true;
    } else {
      router.navigate(['/unauthorized']);
      return false;
    }
  } catch (err) {
    router.navigate(['/login']);
    return false;
  }


};
