import { Component } from '@angular/core';
import { HeaderAuthComponent } from '../../header-auth/header-auth.component'
import {FooterComponent} from '../footer.component';

@Component({
  standalone: true,
  selector: 'app-agb',
  imports: [HeaderAuthComponent, FooterComponent],
  templateUrl: './agb.component.html',
  styleUrls: ['./agb.component.scss', '../../../general.scss']
})
export class AgbComponent {

}
