import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {ModultermineApiService} from '../termine.service';
import {LoggingService} from '../../../../logging.service';
import {SnackbarService} from '../../../../snackbar.service';

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
  log = new LoggingService("AddTerminComponent", "modul-service")
  sb = inject(SnackbarService)

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
          this.log.debug("Termin successfully created")
          this.sb.openInfo("Termin erfolgreich erstellt")
          this.terminErstellt.emit()
          this.initTerminForm()
        },
        error: (err) => {
          this.initTerminForm()
          this.log.error("Could not create termin")
          this.sb.openInfo("Fehler beim erstellen des Termins")
        }
      })
    }
  }

  ngOnInit(): void {
    this.initTerminForm()
  }

  initTerminForm() {
    this.neuerTerminForm = new FormGroup({
      modulId: new FormControl(this.modulId),
      titel: new FormControl("", [Validators.required, Validators.maxLength(200)]),
      beginn: new FormControl("", Validators.required),
      ende: new FormControl(""),
      notiz: new FormControl(""),
      terminart: new FormControl(""),
      repeat: new FormControl("", Validators.required)
    })
  }
}
