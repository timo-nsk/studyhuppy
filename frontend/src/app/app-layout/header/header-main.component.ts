import {Component} from '@angular/core';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {AvatarComponent} from '../../user-profile/avatar/avatar.component';

@Component({
  selector: 'app-header-main',
  imports: [NgIf, RouterLink, AvatarComponent],
  templateUrl: './header-main.component.html',
  standalone: true,
  styleUrls: ['./header-main.component.scss', '../../general.scss', '../../color.scss', '../../links.scss', '../../button.scss']
})
export class HeaderMainComponent {


}
