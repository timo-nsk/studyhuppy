import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-statistics',
  imports: [
    RouterLink,
    RouterOutlet
  ],
  templateUrl: './statistics.component.html',
  standalone: true,
  styleUrls: ['./statistics.component.scss', '../../links.scss', '../../color.scss']
})
export class StatisticsComponent {

}
