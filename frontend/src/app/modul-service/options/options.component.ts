import {Component, OnInit} from '@angular/core';
import {Modul} from '../module/domain';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatFormField, MatLabel} from '@angular/material/input';
import {MatSelect, MatSelectChange} from '@angular/material/select';
import {MatOption} from '@angular/material/core';
import {MatSlideToggle} from '@angular/material/slide-toggle';
import { ModuleApiService } from '../module/module-api.service';

@Component({
  selector: 'app-options',
  imports: [CommonModule, ReactiveFormsModule, FormsModule, MatFormField, MatLabel, MatSelect, MatOption, MatSlideToggle],
  templateUrl: './options.component.html',
  standalone: true,
  styleUrls: ['./options.component.scss', '../../color.scss', '../../general.scss']
})
export class OptionsComponent implements OnInit{

  module : Modul[] = []
  modulFachId : string = ''

  addTimeForm = new FormGroup({
    fachId: new FormControl("", Validators.required),
    time: new FormControl("", Validators.required)
  })

  addKlausurDate = new FormGroup({
    fachId: new FormControl("", Validators.required),
    date: new FormControl("", Validators.required),
    time: new FormControl("", Validators.required)
  })

  constructor(private service : ModuleApiService,
              private fb : FormBuilder,
              private snackbar : MatSnackBar) {}

  ngOnInit(): void {
    this.service.getAllModulesByUsername().subscribe({
      next: (data) => {
        this.module = data;

      },
      error: (err) => {
        console.error('Fehler beim Laden:', err);
      }
    });
  }


  resetTimer() {
    this.service.resetTimer(this.modulFachId).subscribe({
      next: () => this.showConfirmation("Timer zurückgesetzt", 2500, ""),
      error: () => this.showConfirmation("Timer von konnte nicht zurückgesetzt werden", 2500, ""),
    })
  }

  deleteModul(): void {
    this.service.deleteModul(this.modulFachId).subscribe(() => {
      this.module = this.module.filter(modul => modul.fachId !== this.modulFachId);
      this.showConfirmation("Modul erfolgreich gelöscht", 2500, "")
    });
  }

  putAktivStatus(fachId: string) {
    this.service.putAktivStatus(fachId).subscribe(() => {
      this.service.getAllModulesByUsername().subscribe({
        next: (data) => {
          this.module = data;
        },
        error: (err) => {
          console.error('Fehler beim Laden:', err);
        }
      });
      this.showConfirmation("Modulaktivität geändert", 2500, "success-snack")
    })
  }

  sendAddTimeData() {
    this.addTimeForm.patchValue({fachId: this.modulFachId})
    if(this.addTimeForm.valid) {
      let data = this.addTimeForm.value
      this.service.sendAddTimeData(data)
    }
  }

  sendKlausurDateData() {
    this.addKlausurDate.patchValue({fachId: this.modulFachId})
    if(this.addKlausurDate.valid) {
      let data = this.addKlausurDate.value
      this.service.sendKlausurDateData(data)
    }
  }

  showConfirmation(message: string, duration : number, cssClass : string) : void   {
    this.snackbar.open(message, "",  {
      duration: duration,
      panelClass: cssClass,
      horizontalPosition: 'end'
    })
  }

  selectModule(fachId: MatSelectChange<string>) {
    this.modulFachId = fachId.value;
  }

  getActiveStatus(fachId: string) {
    const modul = this.module.find(mod => mod.fachId === fachId);
    let isActive : boolean = modul ? modul.active : false
    //console.log("is active: " + isActive)
    return isActive
  }
}
