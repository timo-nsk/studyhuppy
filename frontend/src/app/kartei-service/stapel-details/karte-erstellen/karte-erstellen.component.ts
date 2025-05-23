import {Component, inject, Input, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatCheckbox} from '@angular/material/checkbox';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow, MatRowDef, MatTable
} from '@angular/material/table';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import {KarteiApiService} from '../../kartei.api.service';

@Component({
  selector: 'app-karte-erstellen',
  imports: [NgIf, ReactiveFormsModule, FormsModule, MatCheckbox, MatCell, MatCellDef, MatColumnDef, MatHeaderCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable, MatHeaderCellDef],
  templateUrl: './karte-erstellen.component.html',
  standalone: true,
  styleUrls: ['./karte-erstellen.component.scss', '../../../button.scss', '../../../forms.scss', '../../../general.scss']
})
export class KarteErstellenComponent implements OnInit{
  karteiService = inject(KarteiApiService)
  snackbar = inject(MatSnackBar)
  router = inject(Router)

  MAX_CHARACTERS : number = 2000
  frageCharsLeft : number = this.MAX_CHARACTERS
  antwortCharsLeft : number = this.MAX_CHARACTERS
  notizCharsLeft: number = this.MAX_CHARACTERS;

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
      frage: new FormControl(null, [Validators.required, Validators.maxLength(this.MAX_CHARACTERS-1)]),
      antwort: new FormControl(null, [Validators.required, Validators.maxLength(this.MAX_CHARACTERS-1)]),
      notiz: new FormControl(null, Validators.maxLength(this.MAX_CHARACTERS-1)),
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


  updateCharsLeft(type : string) {

    if(type == 'frage') {
      let charsUsed = this.neueNormaleFrageForm.get('frage')?.value.length
      this.frageCharsLeft = this.MAX_CHARACTERS - charsUsed
    } else if(type == 'antwort') {
      let charsUsed = this.neueNormaleFrageForm.get('antwort')?.value.length
      this.antwortCharsLeft = this.MAX_CHARACTERS - charsUsed
    } else if(type == 'notiz') {
      let charsUsed = this.neueNormaleFrageForm.get('notiz')?.value.length
      this.notizCharsLeft = this.MAX_CHARACTERS - charsUsed
    }

  }
}
