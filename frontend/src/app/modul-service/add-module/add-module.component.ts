import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {ModuleApiService} from '../module/module-api.service';
import {HttpClient} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';
import {SnackbarService} from '../../snackbar.service';
import {LoggingService} from '../../logging.service';

@Component({
  selector: 'app-add-module',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './add-module.component.html',
  standalone: true,
  styleUrls: ['./add-module.component.scss', './color.scss', '../../forms.scss', '../../general.scss']
})
export class AddModuleComponent {
  sb = inject(SnackbarService)
  service = inject(ModuleApiService)
  http = inject(HttpClient)
  log = new LoggingService("AddModuleComponent", "modul-service")
  lerntageCheckbox : boolean = false

  newModulForm = this.initForm()

  onSubmit() : void {
    if(this.newModulForm.valid) {
      this.log.debug("newModulForm data VALID")
      const form = this.newModulForm.value
      this.service.postFormData(form).subscribe({
        next: () => {
          this.sb.openInfo("Modul erfolgreich erstellt")
        },
        error: (err) => {
          console.log(err)
          this.sb.openError("Sie können keine weiteren Module erstellen")
        }
      })
    } else {
      this.log.debug("newModulForm data INVALID")
    }
  }

  toggleLerntageCheckbox() {
    this.lerntageCheckbox = !this.lerntageCheckbox
    this.newModulForm.controls['mondays'].setValue(false);
    this.newModulForm.controls['tuesdays'].setValue(false);
    this.newModulForm.controls['wednesdays'].setValue(false);
    this.newModulForm.controls['thursdays'].setValue(false);
    this.newModulForm.controls['fridays'].setValue(false);
    this.newModulForm.controls['saturdays'].setValue(false);
    this.newModulForm.controls['sundays'].setValue(false);
  }

  initForm() : FormGroup {
    return new FormGroup({
      name: new FormControl("", [Validators.required]),
      creditPoints: new FormControl(5, [Validators.required, Validators.min(0)]),
      kontaktzeitStunden: new FormControl(1, [Validators.required, Validators.min(1)]),
      selbststudiumStunden: new FormControl(1, [Validators.required, Validators.min(1)]),
      time: new FormControl(""),
      mondays: new FormControl(false),
      tuesdays: new FormControl(false),
      wednesdays: new FormControl(false),
      thursdays: new FormControl(false),
      fridays: new FormControl(false),
      saturdays: new FormControl(false),
      sundays: new FormControl(false)
    })
  }
}
