import { Component, inject } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {KarteiApiService} from '../kartei.api.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import {LoggingService} from '../../logging.service';
import {SnackbarService} from '../../snackbar.service';

@Component({
  selector: 'app-stapel-erstellen',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './stapel-erstellen.component.html',
  standalone: true,
  styleUrls: ['./stapel-erstellen.component.scss', '../../button.scss', '../../color.scss', '../../forms.scss', '../../general.scss']
})
export class StapelErstellenComponent {
  log = new LoggingService("StapelErstellenComponent", "kartei-service")
  sb = inject(SnackbarService)
  karteiService = inject(KarteiApiService)
  snackbar = inject(MatSnackBar)
  router = inject(Router)
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
    if (this.newStapelForm.invalid) {
      this.newStapelForm.markAsTouched()
      this.log.debug("form data is INVALID")
    } else {
      this.log.debug("form data is VALID")
      const data : any = this.newStapelForm.value
      this.karteiService.postNewStapel(data).subscribe({
        next: () => {
          this.log.debug("new stapel data sucessfully sent")
          this.router.navigateByUrl("/kartei")
          this.sb.openInfo("Stapel erfolgreich erstellt")
        },
        error: err => {
          this.log.debug(`error sending form data. reason ${err}`)
          this.sb.openError("Stapel konnte nicht erstellt werden")
        }
      })
    }
  }
}
