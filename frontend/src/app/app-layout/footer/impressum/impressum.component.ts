import { Component } from '@angular/core';
import { HeaderAuthComponent } from '../../header-auth/header-auth.component'
import {FooterComponent} from '../footer.component';

@Component({
  selector: 'app-impressum',
  imports: [HeaderAuthComponent, FooterComponent],
  templateUrl: './impressum.component.html',
  styleUrls: ['./impressum.component.scss', '../../../general.scss']
})
export class ImpressumComponent {

}
