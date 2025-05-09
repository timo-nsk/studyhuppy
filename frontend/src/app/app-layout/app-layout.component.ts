import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink, RouterOutlet} from '@angular/router';
import {FooterComponent} from './footer/footer.component';
import {HeaderMainComponent} from './header-main/header-main.component';

@Component({
  selector: 'app-app-layout',
  imports: [CommonModule, RouterOutlet, RouterLink, FooterComponent, HeaderMainComponent],
  templateUrl: './app-layout.component.html',
  standalone: true,
  styleUrls: ['./app-layout.component.scss', '../general.scss', 'side-navbar.scss']
})
export class AppLayoutComponent {

}
