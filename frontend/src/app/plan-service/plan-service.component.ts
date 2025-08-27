import { Component } from '@angular/core';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-plan-service',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './plan-service.component.html',
  standalone: true,
  styleUrls: ['./plan-service.component.scss', '../general.scss', '../button.scss']
})
export class PlanServiceComponent {

}
