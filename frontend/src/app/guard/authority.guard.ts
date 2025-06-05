import {CanActivateFn, Router} from '@angular/router'
import {inject} from '@angular/core'
import * as jwt from 'jwt-decode'
import {LoggingService} from '../logging.service'

export const authorityGuard: CanActivateFn = (route, state) => {
  const log = new LoggingService("authorityGuard", "app-service")
  const router: Router = inject(Router)
  const token : string = localStorage.getItem("auth_token") ?? ""

  try {
    const decoded: any = jwt.jwtDecode(token)
    const authorities = decoded.authorities || []

    if (authorities.includes('ROLE_ADMIN')) {
      log.debug("User is ADMIN")
      return true
    } else {
      log.debug("User is NOT ADMIN, navigate to /unauthorized")
      router.navigate(['/unauthorized'])
      return false
    }
  } catch (err) {
    log.error("error decoding authority data from auth_token")
    router.navigate(['/login'])
    return false
  }
}
