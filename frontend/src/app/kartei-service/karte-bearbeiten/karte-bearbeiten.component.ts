import {Component, inject, Input, OnInit} from '@angular/core';
import {FrageTyp, Karteikarte} from '../domain';
import {FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
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
  styleUrls: ['./karte-bearbeiten.component.scss', '../../button.scss']
})
export class KarteBearbeitenComponent implements OnInit{
  protected readonly FrageTyp = FrageTyp

  karteiService = inject(KarteiApiService)

  @Input() stapelId! : string | null
  @Input() karteToEdit!: Karteikarte
  editNormaleFrageForm : FormGroup = new FormGroup({})
  editChoiceFrageForm: FormGroup =  new FormGroup({})
  displayedColumns: string[] = ['idx','wahr', 'antwort', 'option'];
  antwortenChoiceForm: FormGroup = new FormGroup({})

  ngOnInit(): void {
    this.editNormaleFrageForm = new FormGroup({
      stapelId: new FormControl(this.stapelId),
      karteId: new FormControl(this.karteToEdit.fachId),
      frage: new FormControl(this.karteToEdit.frage),
      antwort: new FormControl(this.karteToEdit.antwort),
      notiz: new FormControl(this.karteToEdit.notiz)
    })

  }

  putEditedData(frageTyp: string) {
    console.log(this.editNormaleFrageForm.value)
    switch (frageTyp) {
      case 'n': {
        let wasNotEdited = this.dataWasNotEdited()
        console.log("was not edited=" + wasNotEdited)

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

  dataWasNotEdited() {
    const origFrage = this.karteToEdit.frage
    const origAntwort = this.karteToEdit.antwort
    const origNotiz = this.karteToEdit.notiz

    let editedFrage = this.editNormaleFrageForm.get('frage')?.value;
    let editedAntwort = this.editNormaleFrageForm.get('antwort')?.value;
    let editedNotiz = this.editNormaleFrageForm.get('notiz')?.value == '' ? null : this.editNormaleFrageForm.get('notiz')?.value;

    return origFrage == editedFrage &&
      origAntwort == editedAntwort &&
      origNotiz == editedNotiz;

  }
}
