import { Component } from '@angular/core';
import { HeaderAuthComponent } from '../../header-auth/header-auth.component'
import {FooterComponent} from '../footer.component';

@Component({
  selector: 'app-dsgvo',
  imports: [HeaderAuthComponent, FooterComponent],
  templateUrl: './dsgvo.component.html',
  styleUrls: ['./dsgvo.component.scss', '../../../general.scss']
})
export class DsgvoComponent {

}
