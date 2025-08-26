import { Component } from '@angular/core';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-plan-service',
  imports: [
    NgIf
  ],
  templateUrl: './plan-service.component.html',
  standalone: true,
  styleUrls: ['./plan-service.component.scss', '../general.scss', '../button.scss']
})
export class PlanServiceComponent {

}
