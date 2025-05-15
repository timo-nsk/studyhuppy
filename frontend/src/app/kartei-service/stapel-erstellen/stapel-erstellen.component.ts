import { Component, inject } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {KarteiApiService} from '../kartei.api.service';

@Component({
  selector: 'app-stapel-erstellen',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './stapel-erstellen.component.html',
  standalone: true,
  styleUrls: ['./stapel-erstellen.component.scss', '../../button.scss', '../../color.scss']
})
export class StapelErstellenComponent {
  karteiService = inject(KarteiApiService)
  // TODO: der regex ist auch nicht ganz korreket glaube ich
  lernintervalleRegex : string = '^(?:(?:[1-5]?[0-9]m|(?:1[0-9]|2[0-3]|[1-9])h|(?:[1-2][0-9]|3[0-1]|[1-9])d)(?:,(?:[1-5]?[0-9]m|(?:1[0-9]|2[0-3]|[1-9])h|(?:[1-2][0-9]|3[0-1]|[1-9])d))*)$'
  modulMap : any = []

  newStapelForm : FormGroup = new FormGroup({
    setName: new FormControl(null, Validators.required),
    beschreibung: new FormControl(null),
    modulFachId: new FormControl(null),
    lernIntervalle: new FormControl(null, [Validators.required, Validators.pattern(this.lernintervalleRegex)])
  })

  submitForm() {
    if (this.newStapelForm.valid) {
      const data : any = this.newStapelForm.value
      this.karteiService.postNewStapel(data)

    } else {
      console.log("nope")
    }


  }
}
