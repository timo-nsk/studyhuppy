import {Component, OnInit} from '@angular/core';
import {ModuleService} from '../module/module-service';
import {Modul} from '../module/modul';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-options',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './options.component.html',
  standalone: true,
  styleUrls: ['./options.component.scss', '../../color.scss']
})
export class OptionsComponent implements OnInit{

  module : Modul[] = []

  addTimeForms: FormGroup[] = [];

  addKlausurDateForms : FormGroup[] = [];

  fachId : string = ""

  constructor(private service : ModuleService,
              private fb : FormBuilder,
              private snackbar : MatSnackBar) {}

  ngOnInit(): void {
    this.service.getAllModulesByUsername().subscribe({
      next: (data) => {
        this.module = data;

        // Jetzt sind die Daten vorhanden – hier Formulare aufbauen
        this.module.forEach(modul => {
          const form = this.fb.group({
            time: new FormControl(""),
            fachId: [modul.fachId]
          });
          this.addTimeForms.push(form);
        });

        this.module.forEach(modul => {
          const form = this.fb.group({
            date : new FormControl(""),
            time : new FormControl(""),
            fachId : [modul.fachId]
          });
          this.addKlausurDateForms.push(form);
        });
      },
      error: (err) => {
        console.error('Fehler beim Laden:', err);
      }
    });
  }


  resetTimer(fachId : string, name : string) {
    this.service.resetTimer(fachId).subscribe({
      next: () => this.showConfirmation("Timer von '" + name + "' zurückgesetzt", 2500, ""),
      error: () => this.showConfirmation("Timer von '" + name + "' konnte nicht zurückgesetzt werden", 2500, ""),
    })
  }

  deleteModul(fachId: string, name : string): void {
    this.service.deleteModul(fachId).subscribe(() => {
      this.module = this.module.filter(modul => modul.fachId !== fachId);
      this.showConfirmation("Modul '"+ name + "' gelöscht", 2500, "")
    });
  }

  deactivateModul(fachId: string, name : string) {
      this.service.deactivateModul(fachId).subscribe(() => {

        this.service.getAllModulesByUsername().subscribe({
          next: (data) => {
            this.module = data;
          },
          error: (err) => {
            console.error('Fehler beim Laden:', err);
          }
        });
        this.showConfirmation("Modul '"+ name + "' deaktiviert", 2500, "success-snack")
        //window.location.reload();
      })
  }

  activateModul(fachId: string, name : string) {
    this.service.activateModul(fachId).subscribe(() => {
      this.service.getAllModulesByUsername().subscribe({
        next: (data) => {
          this.module = data;
        },
        error: (err) => {
          console.error('Fehler beim Laden:', err);
        }
      });
      this.showConfirmation("Modul '"+ name + "' aktiviert", 2500, "success-snack")

      //window.location.reload();
    })
  }

  sendAddTimeData(index : number, name : string) {
    const data = this.addTimeForms[index].value
    this.service.sendAddTimeData(data)
    this.showConfirmation("Modul '"+ name + "' Zeit hinzugefügt", 2500, "")
  }

  sendKlausurDateData(index : number, name : string) {
    const data = this.addKlausurDateForms[index].value
    this.service.sendKlausurDateData(data)
    this.showConfirmation("Modul '"+ name + "' Klausurdatum gesetzt", 2500, "")
  }

  showConfirmation(message: string, duration : number, cssClass : string) : void   {
    this.snackbar.open(message, "",  {
      duration: duration,
      panelClass: cssClass,
      horizontalPosition: 'end'
    })
  }
}
