import {Component, inject, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {Session, SessionInfoDto} from '../../modul-service/session/session-domain';
import {SessionApiService} from '../../modul-service/session/session-api.service';
import {TimeFormatPipe} from '../../modul-service/module/time-format.pipe';
import {LernplanRequest, TagDto} from '../plan-domain';
import {PlanApiService} from '../plan-api.service';
import {SnackbarService} from '../../snackbar.service';


@Component({
  selector: 'app-create',
  imports: [
    NgForOf,
    ReactiveFormsModule,
    NgIf,
    TimeFormatPipe,
    FormsModule
  ],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss', '../../general.scss', '../../button.scss']
})
export class PlanCreateComponent implements OnInit {
  sessionApiService = inject(SessionApiService)
  planApiService = inject(PlanApiService)
  snackbarService = inject(SnackbarService)
  weekdays = ['Montags', 'Dienstags', 'Mittwochs', 'Donnerstags', 'Freitags', 'Samstags', 'Sonntags'];
  sessionData : SessionInfoDto[] = []
  form!: FormGroup;
  titelForm = new FormGroup({
    lernplanTitel: new FormControl("", Validators.required)
  })

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
      beginn: ['', Validators.required],
      session: ['none', Validators.required]
    });
  }

  get days(): FormArray {
    return this.form.get('days') as FormArray;
  }

  getSessionInfo(fachId: string) {
    return this.sessionData.find(s => s.fachId === fachId);
  }

  save() {
    let invalid = 0

    if (this.titelForm.invalid) {
      this.titelForm.markAllAsTouched()
      invalid++
    }

    for(let i = 0; i < this.days.length; i++) {
      const currForm = this.days.at(i);
      if(currForm.invalid) {
        currForm.markAllAsTouched()
        invalid++
      }
    }

    if (invalid > 0) {
      return
    } else {
      let lernplanRequest: LernplanRequest = {} as LernplanRequest;
      lernplanRequest.lernplanTitel = this.titelForm.value.lernplanTitel!

      let dayDtos : TagDto[] = []
      for(let i = 0; i < this.days.length; i++) {
        const currForm = this.days.at(i);
        let dto : TagDto = this.formToDto(currForm as FormGroup);
        dayDtos.push(dto)
      }
      lernplanRequest.tage = dayDtos

      console.log(lernplanRequest)

      this.planApiService.saveLernplan(lernplanRequest).subscribe({
        next: (response) => {
          this.snackbarService.openSuccess("Lernplan erfolgreich gespeichert")
          // Optionally, reset the form or provide user feedback here
        },
        error: err => {
          this.snackbarService.openError("Fehler beim Speichern des Lernplans")
          console.log(err)
        }
      })
    }
  }

  formToDto(dayForm: FormGroup): TagDto {
    const { weekday, beginn, session } = dayForm.value;
    return new TagDto(weekday, beginn, session);
  }
}
