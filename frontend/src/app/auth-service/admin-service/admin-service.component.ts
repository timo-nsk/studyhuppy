import { Component } from '@angular/core';
import {ActuatorServiceComponent} from './actuator-service/actuator-service.component';

@Component({
  selector: 'app-admin-service',
  imports: [
    ActuatorServiceComponent
  ],
  templateUrl: './admin-service.component.html',
  standalone: true,
  styleUrls: ['./admin-service.component.scss', '../../general.scss']
})
export class AdminServiceComponent {

}
