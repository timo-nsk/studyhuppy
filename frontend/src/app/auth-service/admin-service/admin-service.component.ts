import { Component } from '@angular/core';
import {ActuatorServiceComponent} from './actuator-service/actuator-service.component';
import {UserDbServiceComponent} from './database-services/user-db-service/user-db-service.component';

@Component({
  selector: 'app-admin-service',
  imports: [
    ActuatorServiceComponent,
    UserDbServiceComponent
  ],
  templateUrl: './admin-service.component.html',
  standalone: true,
  styleUrls: ['./admin-service.component.scss', '../../general.scss']
})
export class AdminServiceComponent {

}
