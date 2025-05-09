import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-modul-service',
  imports: [
    RouterLink,
    RouterOutlet
  ],
  templateUrl: './modul-service.component.html',
  standalone: true,
  styleUrls: ['./modul-service.component.scss', '../color.scss', '../links.scss']
})
export class ModulServiceComponent {

}
