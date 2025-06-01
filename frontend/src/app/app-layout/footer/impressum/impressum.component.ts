import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-impressum',
  imports: [
    RouterLink
  ],
  templateUrl: './impressum.component.html',
  standalone: true,
  styleUrls: ['./impressum.component.scss', '../../../general.scss', '../../../color.scss']
})
export class ImpressumComponent {

}
