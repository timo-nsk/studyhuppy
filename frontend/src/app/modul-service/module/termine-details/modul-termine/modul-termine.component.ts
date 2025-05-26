import {Component, Input} from '@angular/core';
import {Modultermin} from '../../domain';
import {MatList, MatListItem} from '@angular/material/list';
import {DatePipe, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-modul-termine',
  imports: [
    MatList,
    MatListItem,
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
