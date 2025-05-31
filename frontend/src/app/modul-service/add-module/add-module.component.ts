import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {ModuleApiService} from '../module/module-api.service';
import {HttpClient} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-module',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './add-module.component.html',
  standalone: true,
  styleUrls: ['./add-module.component.scss', './color.scss', '../../forms.scss', '../../general.scss']
})
export class AddModuleComponent {
  lerntageCheckbox : boolean = false

  constructor(private service : ModuleApiService, private http : HttpClient, private snackbar : MatSnackBar) {
  }

  newModulForm = this.initForm()

  onSubmit() : void {
    const form = this.newModulForm.value

    if(this.checkKlausurFormData()) {
      this.service.postFormData(form).subscribe({
        next: () => {
          this.snackbar.open("Modul erfolgreich erstellt", "dismiss", {
            duration: 4000
          })
        },
        error: (err) => {
          console.log(err)
          this.snackbar.open("Sie können keine weiteren Module erstellen", "dismiss", {
            duration: 4000
          })
        }
      })
    } else {
    }
  }

  checkKlausurFormData() : boolean {
    if(this.newModulForm.controls['klausurDatum'].value == "" && this.newModulForm.controls['time'].value != "") {
      console.log("falseeeeee")
      return false
    } else if(this.newModulForm.controls['klausurDatum'].value != "" && this.newModulForm.controls['time'].value == "") {
      console.log("falseeeeee")
      return false
    }
    return true
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
      klausurDatum: new FormControl(""),
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
