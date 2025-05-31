import { Component } from '@angular/core';
import {ActuatorServiceComponent} from './actuator-service/actuator-service.component';
import {FooterComponent} from '../../app-layout/footer/footer.component'
import { HeaderMainComponent } from '../../app-layout/header-main/header-main.component'

@Component({
  selector: 'app-admin-service',
  imports: [
    ActuatorServiceComponent, FooterComponent, HeaderMainComponent
  ],
  templateUrl: './admin-service.component.html',
  standalone: true,
  styleUrls: ['./admin-service.component.scss', '../../general.scss']
})
export class AdminServiceComponent {

}
