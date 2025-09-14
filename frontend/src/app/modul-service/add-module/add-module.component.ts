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

  newModulForm = this.initForm()

  onSubmit() : void {
    if(this.newModulForm.valid) {
      this.log.debug("newModulForm data VALID")
      const form = this.newModulForm.value
      this.service.postFormData(form).subscribe({
        next: () => {
          this.sb.openInfo("Modul erfolgreich erstellt")
          this.newModulForm.reset()
        },
        error: (err) => {
          console.log(err)
          this.sb.openError("Sie können keine weiteren Module erstellen")
        }
      })
    } else {
      this.newModulForm.markAllAsTouched()
      this.log.debug("newModulForm data INVALID")
    }
  }

  initForm() : FormGroup {
    return new FormGroup({
      name: new FormControl("", [Validators.required]),
      creditPoints: new FormControl("", [Validators.required, Validators.min(0)]),
      kontaktzeitStunden: new FormControl("", [Validators.required, Validators.min(1)]),
      selbststudiumStunden: new FormControl("", [Validators.required, Validators.min(1)]),
      time: new FormControl("")
    })
  }
}
