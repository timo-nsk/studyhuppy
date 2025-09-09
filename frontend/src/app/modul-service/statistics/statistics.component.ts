import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-statistics',
  imports: [RouterLink, RouterOutlet, NgClass
  ],
  templateUrl: './statistics.component.html',
  standalone: true,
  styleUrls: ['./statistics.component.scss', '../../links.scss', '../../color.scss']
})
export class StatisticsComponent {
  isActive : boolean[] = [true, false]

  activatePaginationitem(i: number) {
    for (let j = 0; j < this.isActive.length; j++) {
      if (j == i) {
        this.isActive[j] = true
      } else {
        this.isActive[j] = false
      }
    }
  }
}
