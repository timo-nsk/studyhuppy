import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-help-service',
  templateUrl: './help.component.html',
  styleUrl: './help.component.scss',
  imports: [
    RouterLink
  ],
})
export class HelpComponent {

}

