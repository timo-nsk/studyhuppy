import {Component, Input} from '@angular/core';
import {Modultermin} from '../../domain';
import {DatePipe, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-modul-termine',
  imports: [
    NgForOf,
    DatePipe,
    NgIf
  ],
  templateUrl: './modul-termine.component.html',
  standalone: true,
  styleUrls: ['./modul-termine.component.scss', '../../../../color.scss']
})
export class ModulTermineComponent {
  @Input() modultermine!: Modultermin[];

}
