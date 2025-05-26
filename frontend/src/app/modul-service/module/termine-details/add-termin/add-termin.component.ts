import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {ModultermineApiService} from '../termine.service';

@Component({
  selector: 'app-add-termin',
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './add-termin.component.html',
  standalone: true,
  styleUrls: ['./add-termin.component.scss', '../../../../forms.scss', '../../../../color.scss']
})
export class AddTerminComponent implements OnInit{

  @Input() modulId!: string;
  @Output() terminErstellt = new EventEmitter<void>();
  neuerTerminForm: FormGroup = new FormGroup({})

  terminService = inject(ModultermineApiService);


  sendTerminFormData() {
    const data = this.neuerTerminForm.value;
    if(this.neuerTerminForm.invalid) {
      this.neuerTerminForm.markAllAsTouched();
      return;
    } else {
      this.terminService.postNeuerTermin(data).subscribe({
        next: () => {
          console.log("Termin erfolgreich erstellt");
          this.terminErstellt.emit()
          this.neuerTerminForm.reset();
        },
        error: (err) => {
          console.error("Fehler beim Erstellen des Termins:", err)
        }
      })
    }

  }

  ngOnInit(): void {
    this.neuerTerminForm = new FormGroup({
      modulId: new FormControl(this.modulId),
      titel: new FormControl("", [Validators.required, Validators.maxLength(200)]),
      beginn: new FormControl("", Validators.required),
      ende: new FormControl(""),
      notiz: new FormControl(""),
      repeat: new FormControl("", Validators.required)
    })
  }
}

