import {Component, inject, Input, OnInit} from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from "@angular/forms";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {TimeFormatPipe} from "../../modul-service/module/time-format.pipe";
import {SessionApiService} from '../../session-service/session-api.service';
import {PlanApiService} from '../plan-api.service';
import {SnackbarService} from '../../snackbar.service';
import {SessionInfoDto} from '../../session-service/session-domain';
import {Lernplan, LernplanRequest, TagDto} from '../plan-domain';

@Component({
  selector: 'app-plan-form',
  imports: [
    NgForOf,
    ReactiveFormsModule,
    NgIf,
    TimeFormatPipe,
    FormsModule,
    NgClass
  ],
  templateUrl: './plan-form.component.html',
  styleUrls: ['./plan-form.component.scss', '../../general.scss', '../../button.scss', '../../color.scss']
})
export class PlanFormComponent implements OnInit{
  sessionApiService = inject(SessionApiService)
  planApiService = inject(PlanApiService)
  snackbarService = inject(SnackbarService)
  weekdays = ['Montags', 'Dienstags', 'Mittwochs', 'Donnerstags', 'Freitags', 'Samstags', 'Sonntags'];
  weekdaysEnum = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];
  sessionData : SessionInfoDto[] = []

  form!: FormGroup;

  titelForm = new FormGroup({
    lernplanTitel: new FormControl("", Validators.required)
  })
  gesamtzeit : number = 0

  @Input() lernplanToEdit! : Lernplan

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      days: this.fb.array(this.weekdays.map(day => this.createDayForm(day)))
    });

    this.disableBeginnInputOnSessionNone()

    this.sessionApiService.getLernplanSessionData().subscribe({
      next: (data) => {
        this.sessionData = data
        //console.log("Plan-Form lernplanToEdit: ", this.lernplanToEdit)

        if(this.hasLernplanToEdit()) {
          this.fillFormForEdit()
        }

      },
      error: (err) => {
        console.error('Error fetching session data:', err);
      }
    })

  }

  createDayForm(day: string): FormGroup {
    return this.fb.group({
      weekday: [day],
      beginn: ['00:00', Validators.required],
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

  updateGesamtzeit() {
    this.gesamtzeit = 0
    let days = this.days
    for(let i = 0; i < days.length; i++) {
      const currForm = days.at(i);
      const { session } = currForm.value;
      if (session !== 'none') {
        let sessionInfo = this.getSessionInfo(session)
        if (sessionInfo) {
          this.gesamtzeit += sessionInfo.zeit
        }
      }
    }
    console.log("updated gesamtzeit")
  }

  disableBeginnInputOnSessionNone() {
    for (let day of this.days.controls) {
      const beginnControl = day.get('beginn');

      if (day.get('session')?.value == 'none') {
        beginnControl?.disable({ emitEvent: false });
      }

      day.get('session')?.valueChanges.subscribe(value => {
        if (value == 'none') {
          beginnControl?.disable({ emitEvent: false });
        } else {
          beginnControl?.enable({ emitEvent: false });
        }
      });
    }
  }

  /**
   * Befüllt das Form-Objekt mit den Daten aus dem lernplanToEdit-Objekt
   */
  fillFormForEdit() {
    this.titelForm.patchValue({lernplanTitel: this.lernplanToEdit.titel})

    this.days.controls.forEach((formGroup: AbstractControl, index: number) => {
      const currentDay = this.weekdaysEnum[index]
      const tagDto = this.lernplanToEdit.tagesListe.find(tagDto => tagDto.tag === currentDay);

      if(tagDto) {
        const dayGroup = formGroup as FormGroup;

        dayGroup.patchValue({beginn: tagDto.beginn})
        dayGroup.patchValue({session: tagDto.sessionId})
      }
    });
  }

  hasLernplanToEdit() : boolean {
    return this.lernplanToEdit != null && this.lernplanToEdit != undefined
  }
}
