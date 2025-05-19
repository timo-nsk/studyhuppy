import {Component, Input} from '@angular/core';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-normal-karte',
  imports: [
    NgIf
  ],
  templateUrl: './normal-karte.component.html',
  standalone: true,
  styleUrls: ['./normal-karte.component.scss', '../lernen.component.scss']
})
export class NormalKarteComponent {

  @Input() frage : string | undefined;
  @Input() antwort : string | undefined;
  @Input() hideAntwort : boolean = true;

}
