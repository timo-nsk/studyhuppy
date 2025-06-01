import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-dsgvo',
  imports: [
    RouterLink
  ],
  templateUrl: './dsgvo.component.html',
  standalone: true,
  styleUrls: ['./dsgvo.component.scss', '../../../general.scss', '../../../color.scss']
})
export class DsgvoComponent {

}
