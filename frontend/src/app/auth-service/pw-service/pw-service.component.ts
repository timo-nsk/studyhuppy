import { Component } from '@angular/core';
import {HeaderAuthComponent} from '../../app-layout/header-auth/header-auth.component';

@Component({
  selector: 'app-pw-service',
  imports: [
    HeaderAuthComponent
  ],
  templateUrl: './pw-service.component.html',
  standalone: true,
  styleUrls: ['./pw-service.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss']
})
export class PwServiceComponent {

}
