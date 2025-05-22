import {AfterViewInit, Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {FrageTyp, Karteikarte} from '../domain';
import {FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow, MatRowDef, MatTable
} from '@angular/material/table';
import {MatCheckbox} from '@angular/material/checkbox';
import {NgIf} from '@angular/common';
import {KarteiApiService} from '../kartei.api.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {group} from '@angular/animations';

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
    ReactiveFormsModule,
    MatHeaderCellDef
  ],
  templateUrl: './karte-bearbeiten.component.html',
  standalone: true,
  styleUrls: ['./karte-bearbeiten.component.scss', '../../button.scss', '../../forms.scss', '../../general.scss']
})
export class KarteBearbeitenComponent implements OnInit, AfterViewInit{
  protected readonly FrageTyp = FrageTyp

  karteiService = inject(KarteiApiService)
  snackbar = inject(MatSnackBar)

  @Input() stapelId! : string | null
  @Input() karteToEdit!: Karteikarte
  editNormaleFrageForm : FormGroup = new FormGroup({})
  editChoiceFrageForm: FormGroup =  new FormGroup({})
  displayedColumns: string[] = ['idx','wahr', 'antwort', 'option'];
  editAntwortenChoiceForm: FormGroup = new FormGroup({})
  anzahlAntworten : number | undefined = 0
  notEdited : boolean = true
  MAX_CHARACTERS : number = 2000
  charsLeft: number = this.MAX_CHARACTERS

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

    this.editChoiceFrageForm = new FormGroup({
      stapelId: new FormControl(this.stapelId),
      karteId: new FormControl(this.karteToEdit.fachId),
      frage: new FormControl(this.karteToEdit.frage, Validators.required),
      antworten: new FormArray(
        this.karteToEdit.antworten!.map((antwort: any) =>
          new FormGroup({
            wahrheit: new FormControl(antwort.wahrheit),
            antwort: new FormControl(antwort.antwort, Validators.required)
          })
        )
      ),
      notiz: new FormControl(this.karteToEdit.notiz)
    })

    this.editAntwortenChoiceForm = new FormGroup({
      wahrheit: new FormControl(false),
      antwort: new FormControl('', Validators.required)
    });

    this.anzahlAntworten = this.karteToEdit.antworten?.length
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

  removeAntwort(i: number) {
    (this.editChoiceFrageForm.get('antworten') as FormArray).removeAt(i)
    console.log("removed antwort at index:" + i)
  }

  addAntwort() {
    const wahr = this.editChoiceFrageForm.get('wahrheit')?.value;
    const text = this.editChoiceFrageForm.get('antwort')?.value;

    if (!text) return;

    const antwortGroup = new FormGroup({
      wahrheit: new FormControl(wahr),
      antwort: new FormControl(text)
    });

    this.antwortenArray.push(antwortGroup);
    this.editAntwortenChoiceForm.reset({ wahrheit: false, antwort: '' });
  }

  get antwortenArray(): FormArray {
    let arr = this.editChoiceFrageForm.get('antworten') as FormArray;
    return arr
  }

  get antwortenArrayRes(): [boolean, string][] {
    let arr = this.editChoiceFrageForm.get('antworten') as FormArray;
    const controls = arr?.controls;
    if (!controls) return [];
    return controls.map(control => {
      const group = control as FormGroup;
      return [
        group.get('wahrheit')?.value ?? false,
        group.get('antwort')?.value ?? ''
      ];
    });
  }

  updateCharsLeft() {

  }

  ngAfterViewInit(): void {
    console.log(this.antwortenArrayRes)
  }
}
