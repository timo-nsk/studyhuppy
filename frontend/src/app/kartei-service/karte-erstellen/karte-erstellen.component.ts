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
      frageTyp: new FormControl(null),
      frage: new FormControl(null, Validators.required),
      notiz: new FormControl(null),
      antworten: new FormArray([])
    })

    this.antwortenChoiceForm = new FormGroup({
      wahrheit: new FormControl(false),
      antwort: new FormControl('', Validators.required)
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
      return [group.get('wahrheit')?.value, group.get('antwort')?.value];
    });
  }

  lengthAntwortenArray() {
    let arr = this.antwortenArray.controls.map(control => {
      const group = control as FormGroup;
      return [group.get('wahrheit')?.value, group.get('antwort')?.value];
    });

    return arr.length
  }

  addAntwort() {
    const wahr = this.antwortenChoiceForm.get('wahrheit')?.value;
    const text = this.antwortenChoiceForm.get('antwort')?.value;

    if (!text) return;

    const antwortGroup = new FormGroup({
      wahrheit: new FormControl(wahr),
      antwort: new FormControl(text)
    });

    this.antwortenArray.push(antwortGroup);
    this.antwortenChoiceForm.reset({ wahrheit: false, antwort: '' });
  }


  sendNeuekarteData(kartenTyp : string) {
    //console.log("ping sendNeueKarteData")
    if (kartenTyp == 'n') {
      if(this.neueNormaleFrageForm.invalid) {
        //console.log("invalid inputs for normalefrageform")
        this.neueNormaleFrageForm.markAllAsTouched()
      } else {
        //console.log("valid inputs for normalefrageform")
        const data = this.neueNormaleFrageForm.value
        this.karteiService.sendNeuekarteData(data, 'n').subscribe({
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
      if(this.neueChoiceFrageForm.invalid) {
        //console.log("invalid inputs for neuechoicefrageform")
      } else {
        //console.log("valid inputs for neuechoicefrageform")
        const frageTyp = this.decideFrageTyp(this.antwortenArrayRes)
        this.neueChoiceFrageForm.patchValue({ frageTyp: frageTyp });
        const data = this.neueChoiceFrageForm.value
        this.karteiService.sendNeuekarteData(data, 'c').subscribe({
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
    }


  }

  removeAntwort(i: number) {
    (this.neueChoiceFrageForm.get('antworten') as FormArray).removeAt(i)
    console.log("removed antwort at index:" + i)
  }

  countTrueAnswers(arr: [boolean, string][]) {
    let c = 0
    for(let i = 0; i < arr.length; i++) {
      if (arr[0]) c++
    }
    return c
  }

  decideFrageTyp(arr: [boolean, string][]) : string {
    const c = this.countTrueAnswers(arr)
    if(c == 1) return 'SINGLE_CHOICE'
    return 'MULTIPLE_CHOICE'
  }
}
