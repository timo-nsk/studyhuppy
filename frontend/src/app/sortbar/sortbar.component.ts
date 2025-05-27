import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgClass, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-sortbar',
  imports: [
    NgForOf,
    NgIf,
    NgClass
  ],
  templateUrl: './sortbar.component.html',
  standalone: true,
  styleUrl: './sortbar.component.scss'
})
export class SortbarComponent implements OnInit{
  @Input() sortItems: { titel: string; feld: string; }[] = []
  @Output() sortAttribut = new EventEmitter<[string, boolean]>()
  // Alle Daten sind angenommen schon asc sortiert und werden beim ersten klick asc sortiert
  shouldSortAsc : boolean[] = []
  currentSortedItemindex : number | null = null

  sort(feld : string, index : number) {
    // nach klick solle daten so sortiert werden
    let asc = this.shouldSortAsc[index]
    // sortiere daten im parent nach asc/desc
    this.sortAttribut.emit([feld, asc])
    //Daten sind nun sortiert, der nächste soll die daten entgegengesetzt sortieren
    this.shouldSortAsc[index] = !this.shouldSortAsc[index]
    this.currentSortedItemindex = index

    this.invert(index)
    console.log(this.shouldSortAsc)
  }

  initSortedAsc() {
    for(let i = 0; i < this.sortItems?.length; i++) this.shouldSortAsc.push(true)
  }

  invert(index : number) {
    for(let i = 0; i < this.sortItems?.length; i++) {
      if(i != index) {
        this.shouldSortAsc[i] = true
      }
    }
  }

  ngOnInit(): void {
    this.initSortedAsc()
  }
}
