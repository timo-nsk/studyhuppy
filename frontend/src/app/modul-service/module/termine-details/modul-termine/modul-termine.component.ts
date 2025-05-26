import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-modul-termine',
  imports: [],
  templateUrl: './modul-termine.component.html',
  standalone: true,
  styleUrl: './modul-termine.component.scss'
})
export class ModulTermineComponent {
  @Input() modultermine!: any;

}
