import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {FrageTyp, Karteikarte} from '../domain';
import {FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow, MatRowDef, MatTable
} from '@angular/material/table';
import {MatCheckbox} from '@angular/material/checkbox';
import {NgIf} from '@angular/common';
import {KarteiApiService} from '../kartei.api.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-karte-bearbeiten',
  imports: [
    FormsModule,
    MatCell,
    MatCellDef,
    MatCheckbox,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './karte-bearbeiten.component.html',
  standalone: true,
  styleUrls: ['./karte-bearbeiten.component.scss', '../../button.scss', '../../forms.scss']
})
export class KarteBearbeitenComponent implements OnInit{
  protected readonly FrageTyp = FrageTyp

  karteiService = inject(KarteiApiService)
  snackbar = inject(MatSnackBar)

  @Input() stapelId! : string | null
  @Input() karteToEdit!: Karteikarte
  editNormaleFrageForm : FormGroup = new FormGroup({})
  editChoiceFrageForm: FormGroup =  new FormGroup({})
  displayedColumns: string[] = ['idx','wahr', 'antwort', 'option'];
  antwortenChoiceForm: FormGroup = new FormGroup({})
  notEdited : boolean = true

  ngOnInit(): void {
    this.editNormaleFrageForm = new FormGroup({
      stapelId: new FormControl(this.stapelId),
      karteId: new FormControl(this.karteToEdit.fachId),
      frage: new FormControl(this.karteToEdit.frage, Validators.required),
      antwort: new FormControl(this.karteToEdit.antwort, Validators.required),
      notiz: new FormControl(this.karteToEdit.notiz)
    })

    this.editNormaleFrageForm.valueChanges.subscribe(formValue => {
      const origFrage = this.karteToEdit.frage
      const origAntwort = this.karteToEdit.antwort
      const origNotiz = this.karteToEdit.notiz

      let editedFrage = this.editNormaleFrageForm.get('frage')?.value;
      let editedAntwort = this.editNormaleFrageForm.get('antwort')?.value;
      let editedNotiz = this.editNormaleFrageForm.get('notiz')?.value == '' ? null : this.editNormaleFrageForm.get('notiz')?.value;

      this.notEdited = origFrage == editedFrage &&
        origAntwort == editedAntwort &&
        origNotiz == editedNotiz
    });

  }

  putEditedData(frageTyp: string) {
    console.log(this.editNormaleFrageForm.value)
    switch (frageTyp) {
      case 'n': {
        if(this.editNormaleFrageForm.invalid) {
          this.editNormaleFrageForm.markAsTouched()
          return
        } else {
          const data = this.editNormaleFrageForm.value
          this.karteiService.putEditedData(data).subscribe({
            next: () => {
              this.snackbar.open("Karteikarte erfolgreich geändert", "schließen", {
                duration: 3500
              })
            },
            error: () => {
              this.snackbar.open("Karteikarte konnte nicht geändert werden", "schließen", {
                duration: 3500
              })
            }
          })
        }
      }
    }
  }

  lengthAntwortenArray() : number {
    return 10
  }

  removeAntwort(i : number) {

  }

  addAntwort() {

  }

  get antwortenArray(): FormArray {
    return this.editChoiceFrageForm.get('antworten') as FormArray;
  }

  get antwortenArrayRes(): [boolean, string][] {
    return this.antwortenArray.controls.map(control => {
      const group = control as FormGroup;
      return [group.get('wahrheit')?.value, group.get('antwort')?.value];
    });
  }
}
