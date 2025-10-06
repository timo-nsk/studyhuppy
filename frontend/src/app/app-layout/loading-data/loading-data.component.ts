import {Component, Input} from '@angular/core';
import {MatProgressSpinner} from '@angular/material/progress-spinner';

@Component({
  selector: 'Loading',
  imports: [MatProgressSpinner,],
  templateUrl: './loading-data.component.html',
  standalone: true,
  styleUrl: './loading-data.component.scss'
})
export class LoadingDataComponent {
  @Input() entityName : string = "Daten"
}
