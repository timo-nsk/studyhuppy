import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-agb',
  imports: [
    RouterLink
  ],
  templateUrl: './agb.component.html',
  styleUrls: ['./agb.component.scss', '../../../general.scss', '../../../color.scss']
})
export class AgbComponent {

}
