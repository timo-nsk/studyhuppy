import {Component, OnInit} from '@angular/core';
import {LernblockComponent} from './lernblock/lernblock.component';
import {PausenblockComponent} from './pausenblock/pausenblock.component';
import {FormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';


@Component({
  selector: 'app-session',
  imports: [LernblockComponent, PausenblockComponent, FormsModule, NgForOf],
  templateUrl: './session.component.html',
  standalone: true,
  styleUrl: './session.component.scss'
})
export class SessionComponent implements OnInit{
  anzahlBloecke : number = 2;

  ngOnInit(): void {
    console.log(this.anzahlBloecke)
  }

  print() : void {
    console.log(this.anzahlBloecke)
  }
}
