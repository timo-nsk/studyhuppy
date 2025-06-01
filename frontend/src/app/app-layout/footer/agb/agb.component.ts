import { Component } from '@angular/core';
import {FooterComponent} from '../footer.component';

@Component({
  standalone: true,
  selector: 'app-agb',
  imports: [FooterComponent],
  templateUrl: './agb.component.html',
  styleUrls: ['./agb.component.scss', '../../../general.scss']
})
export class AgbComponent {

}
