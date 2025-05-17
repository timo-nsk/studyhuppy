import {Component, inject, Input, OnInit} from '@angular/core';
import {NgIf, NgFor} from '@angular/common';
import {FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-karte-erstellen',
  imports: [NgIf, NgFor, ReactiveFormsModule, FormsModule],
  templateUrl: './karte-erstellen.component.html',
  standalone: true,
  styleUrls: ['./karte-erstellen.component.scss', '../../button.scss']
})
export class KarteErstellenComponent implements OnInit{
  chosenFragenTyp : string = "n"
  @Input() stapelId!: string | null;

  neueNormaleFrageForm : FormGroup = new FormGroup({});

  neueChoiceFrageForm : FormGroup = new FormGroup({});
  antwortenChoiceForm: FormGroup = new FormGroup({})

  ngOnInit(): void {
    this.neueNormaleFrageForm = new FormGroup({
      stapelId: new FormControl(this.stapelId, Validators.required),
      frage: new FormControl(null, Validators.required),
      antwort: new FormControl(null, Validators.required),
      notiz: new FormControl(null),
    })

    this.neueChoiceFrageForm = new FormGroup({
      stapelId: new FormControl(this.stapelId, Validators.required),
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

  // Getter für das FormArray
  get antwortenArray(): FormArray {
    return this.neueChoiceFrageForm.get('antworten') as FormArray;
  }

  // Getter für die Ausgabeansicht
  get antwortenArrayRes(): [boolean, string][] {
    return this.antwortenArray.controls.map(control => {
      const group = control as FormGroup;
      return [group.get('wahrheit')?.value, group.get('aw')?.value];
    });
  }

  addAntwort() {
    const wahr = this.antwortenChoiceForm.get('wahrheit')?.value;
    const text = this.antwortenChoiceForm.get('aw')?.value;

    if (!text) return; // Leere Antwort ignorieren

    const antwortGroup = new FormGroup({
      wahrheit: new FormControl(wahr),
      aw: new FormControl(text)
    });

    this.antwortenArray.push(antwortGroup);
    this.antwortenChoiceForm.reset({ wahrheit: false, aw: '' }); // Zurücksetzen fürs nächste Tupel
    console.log(this.neueNormaleFrageForm)
  }


  sendNeuekarteData(kartenTyp : string) {
    if (kartenTyp == 'n') {
      if(!this.neueNormaleFrageForm.invalid) {
        console.log(this.neueNormaleFrageForm.value)
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
