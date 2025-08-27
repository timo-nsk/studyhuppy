import {Component, inject, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Session, SessionInfoDto} from '../../modul-service/session/session-domain';
import {SessionApiService} from '../../modul-service/session/session-api.service';
import {TimeFormatPipe} from '../../modul-service/module/time-format.pipe';

@Component({
  selector: 'app-create',
  imports: [
    NgForOf,
    ReactiveFormsModule,
    NgIf,
    TimeFormatPipe
  ],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss', '../../general.scss', '../../button.scss']
})
export class PlanCreateComponent implements OnInit {
  sessionApiService = inject(SessionApiService)
  weekdays = ['Montags', 'Dienstags', 'Mittwochs', 'Donnerstags', 'Freitags', 'Samstags', 'Sonntags'];
  sessionData : SessionInfoDto[] = []
  form!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      days: this.fb.array(this.weekdays.map(day => this.createDayForm(day)))
    });

    this.sessionApiService.getLernplanSessionData().subscribe({
      next: (data) => {
        this.sessionData = data
        console.log(this.sessionData)
      },
      error: (err) => {
        console.error('Error fetching session data:', err);
      }
    })
  }

  createDayForm(day: string): FormGroup {
    return this.fb.group({
      weekday: [day],
      beginn: [''],
      session: ['none']
    });
  }

  get days(): FormArray {
    return this.form.get('days') as FormArray;
  }

  getSessionInfo(fachId: string) {
    return this.sessionData.find(s => s.fachId === fachId);
  }

  save() {
    console.log(this.form.value);
    /*
    [
      { weekday: 'Montag', beginn: '08:00', session: 'Montags intensiv', dauer: '99h 30min' },
      { weekday: 'Dienstag', beginn: '', ... },
      ...
    ]
    */
  }

}
