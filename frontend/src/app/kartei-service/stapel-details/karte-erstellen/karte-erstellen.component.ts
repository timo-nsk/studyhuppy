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
import {Router} from '@angular/router';
import {KarteiApiService} from '../../kartei.api.service';
import {SnackbarService} from '../../../snackbar.service';
import {LoggingService} from '../../../logging.service';

@Component({
  selector: 'app-karte-erstellen',
  imports: [NgIf, ReactiveFormsModule, FormsModule, MatCheckbox, MatCell, MatCellDef, MatColumnDef, MatHeaderCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable, MatHeaderCellDef],
  templateUrl: './karte-erstellen.component.html',
  standalone: true,
  styleUrls: ['./karte-erstellen.component.scss', '../../../button.scss', '../../../forms.scss', '../../../general.scss']
})
export class KarteErstellenComponent implements OnInit{
  log = new LoggingService("KarteErstellenComponent", "kartei-service")
  karteiService = inject(KarteiApiService)
  sb = inject(SnackbarService)
  router = inject(Router)

  @Input() stapelId!: string | null;

  MAX_CHARACTERS : number = 2000
  frageCharsLeft : number = this.MAX_CHARACTERS
  antwortCharsLeft : number = this.MAX_CHARACTERS
  notizCharsLeft: number = this.MAX_CHARACTERS;
  chosenFragenTyp : string = "n"

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
    this.log.debug(`added antwort to kartei choice form. ${this.antwortenArray}`);
  }

  sendNeuekarteData(kartenTyp : string) {
    if (kartenTyp == 'n') {
      if(this.neueNormaleFrageForm.invalid) {
        this.log.debug("normale frage form is INVALID")
        this.neueNormaleFrageForm.markAllAsTouched()
      } else {
        this.log.debug("normale frage form is VALID")
        const data = this.neueNormaleFrageForm.value
        this.karteiService.sendNeuekarteData(data, 'n').subscribe({
          next: () => {
            this.sb.openInfo("Karteikarte erstellt")
            this.log.debug("successfully created normale karteikarte")
          },
          error: err => {
            this.log.error(`error while creating normale karteikarte. reason: ${err}`)
            this.sb.openError("Karteikarte konnte nicht erstellt werden")
          },
          complete: () => {
            this.router.navigateByUrl('/stapel-details/' + this.stapelId)
          }
        })
      }
    } else if(kartenTyp == 'c') {
      if(this.neueChoiceFrageForm.invalid) {
        this.neueChoiceFrageForm.markAsTouched()
        this.log.debug("choice frage form is INVALID")
      } else {
        this.log.debug("choice frage form is VALID")
        const frageTyp = this.decideFrageTyp(this.antwortenArrayRes)
        this.neueChoiceFrageForm.patchValue({ frageTyp: frageTyp });
        const data = this.neueChoiceFrageForm.value
        this.karteiService.sendNeuekarteData(data, 'c').subscribe({
          next: () => {
            this.log.debug("successfully created choice karteikarte")
            this.sb.openInfo("Karteikarte erstellt")
          },
          error: err => {
            this.log.error(`error while creating choice karteikarte. reason: ${err}`)
            this.sb.openError("Karteikarte konnte nicht erstellt werden")
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
    this.log.info(`removed antwort from form at index: ${i}}`)
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
