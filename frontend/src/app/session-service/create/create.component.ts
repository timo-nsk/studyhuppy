import {Component, inject, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {Session} from '../session-domain';
import {Modul} from '../../modul-service/module/domain';
import {ModuleApiService} from '../../modul-service/module/module-api.service';
import {SessionFormComponent} from '../session-form/session-form.component';

@Component({
  selector: 'app-session-create',
  imports: [FormsModule, ReactiveFormsModule, SessionFormComponent],
  templateUrl: './create.component.html',
  standalone: true,
  styleUrls: ['./create.component.scss', '../../general.scss', '../../button.scss', '../../forms.scss', '../../color.scss']
})
export class SessionCreateComponent implements OnInit{
  session: Session = {} as Session
  module : Modul[] = []
  modulService = inject(ModuleApiService)

  ngOnInit(): void {
    this.modulService.getAllModulesByUsername().subscribe(
      { next: data => { this.module = data } }
    )
  }
}
