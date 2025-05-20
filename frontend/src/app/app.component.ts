import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet} from '@angular/router';
import {Title} from '@angular/platform-browser';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  imports: [
    CommonModule,
    RouterOutlet,
    MatSnackBarModule
  ],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrls: ['./app.component.scss', './general.scss', './app-layout/side-navbar.scss']
})
export class AppComponent implements  OnInit{
  isAdmin : boolean = true
  constructor( private titleService : Title) {
  }

  ngOnInit() {
    this.titleService.setTitle('Studyhub');
  }
}
