import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-help-service',
  templateUrl: './help.component.html',
  styleUrls: ['./help.component.scss', '../../../general.scss', '../../../color.scss'],
  imports: [RouterLink],
})
export class HelpComponent {

}

