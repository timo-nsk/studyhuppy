import {Component, inject, Input, OnInit} from '@angular/core';
import {NgIf, NgFor} from '@angular/common';
import {FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatCheckbox} from '@angular/material/checkbox';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatList, MatListItem} from '@angular/material/list';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow, MatRowDef, MatTable
} from '@angular/material/table';
import {KarteiApiService} from '../kartei.api.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-karte-erstellen',
  imports: [NgIf, NgFor, ReactiveFormsModule, FormsModule, MatCheckbox, MatFormField, MatInput, MatLabel, MatFormField, MatList, MatListItem, MatCell, MatCellDef, MatColumnDef, MatHeaderCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable, MatHeaderCellDef],
  templateUrl: './karte-erstellen.component.html',
  standalone: true,
  styleUrls: ['./karte-erstellen.component.scss', '../../button.scss']
})
export class KarteErstellenComponent implements OnInit{
  karteiService = inject(KarteiApiService)
  snackbar = inject(MatSnackBar)
  router = inject(Router)

  chosenFragenTyp : string = "n"
  @Input() stapelId!: string | null;

  neueNormaleFrageForm : FormGroup = new FormGroup({});

  neueChoiceFrageForm : FormGroup = new FormGroup({});
  antwortenChoiceForm: FormGroup = new FormGroup({})
  displayedColumns: string[] = ['idx','wahr', 'antwort', 'option'];

  ngOnInit(): void {
    this.neueNormaleFrageForm = new FormGroup({
      stapelId: new FormControl(this.stapelId, Validators.required),
      frageTyp: new FormControl("NORMAL"),
      frage: new FormControl(null, Validators.required),
      antwort: new FormControl(null, Validators.required),
      notiz: new FormControl(null),
    })

    this.neueChoiceFrageForm = new FormGroup({
      stapelId: new FormControl(this.stapelId, Validators.required),
      frageTyp: new FormControl("CHOICE"),
      frage: new FormControl(null, Validators.required),
      notiz: new FormControl(null),
      antworten: new FormArray([])
    })

    this.antwortenChoiceForm = new FormGroup({
      wahrheit: new FormControl(false),
      aw: new FormControl('', Validators.required)
    });
  }

  choseFragetyp(choice: string) {
    if (choice == "n") this.chosenFragenTyp = "n"
    else if (choice == "c") this.chosenFragenTyp = "c"
  }

  get antwortenArray(): FormArray {
    return this.neueChoiceFrageForm.get('antworten') as FormArray;
  }

  get antwortenArrayRes(): [boolean, string][] {
    return this.antwortenArray.controls.map(control => {
      const group = control as FormGroup;
      return [group.get('wahrheit')?.value, group.get('aw')?.value];
    });
  }

  lengthAntwortenArray() {
    let arr = this.antwortenArray.controls.map(control => {
      const group = control as FormGroup;
      return [group.get('wahrheit')?.value, group.get('aw')?.value];
    });

    return arr.length
  }

  addAntwort() {
    const wahr = this.antwortenChoiceForm.get('wahrheit')?.value;
    const text = this.antwortenChoiceForm.get('aw')?.value;

    if (!text) return;

    const antwortGroup = new FormGroup({
      wahrheit: new FormControl(wahr),
      aw: new FormControl(text)
    });

    this.antwortenArray.push(antwortGroup);
    this.antwortenChoiceForm.reset({ wahrheit: false, aw: '' }); // Zurücksetzen fürs nächste Tupel
    console.log(this.neueNormaleFrageForm)
  }


  sendNeuekarteData(kartenTyp : string) {
    console.log("ping sendNeueKarteData")
    if (kartenTyp == 'n') {
      if(this.neueNormaleFrageForm.invalid) {
        //console.log("invalid inputs for normalefrageform")
        this.neueNormaleFrageForm.markAllAsTouched()
      } else {
        //console.log("valid inputs for normalefrageform")
        const data = this.neueNormaleFrageForm.value
        this.karteiService.sendNeuekarteData(data).subscribe({
          next: () => {
            this.snackbar.open("Karteikarte erstellt", "schließen", {
              duration: 3500
            })
          },
          error: () => {
            this.snackbar.open("Karteikarte konnte nicht erstellt werden", "schießen", {
              duration: 3500
            })
          },
          complete: () => {
            this.router.navigateByUrl('/stapel-details/' + this.stapelId)
          }
        })
      }
    } else if(kartenTyp == 'c') {
      if(!this.neueChoiceFrageForm.invalid) {
        console.log(this.neueChoiceFrageForm.value)
      }
    }


  }

  removeAntwort(i: number) {
    (this.neueChoiceFrageForm.get('antworten') as FormArray).removeAt(i)
    console.log("removed antwort at index:" + i)
  }
}
