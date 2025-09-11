import {Component} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-session',
  imports: [FormsModule, ReactiveFormsModule, RouterLink, MatIcon],
  templateUrl: './session.component.html',
  standalone: true,
  styleUrls: ['./session.component.scss', '../general.scss', '../button.scss']
})
export class SessionComponent{ }
