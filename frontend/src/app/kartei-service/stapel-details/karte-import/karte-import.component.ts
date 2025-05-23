import {Component, inject, Input} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {KarteiApiService} from '../../kartei.api.service';
import {MatSnackBar} from '@angular/material/snack-bar';

declare var bootstrap: any;

@Component({
  selector: 'app-karte-import',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './karte-import.component.html',
  standalone: true,
  styleUrls: ['./karte-import.component.scss', '../../../forms.scss']
})
export class KarteImportComponent {
  karteiService = inject(KarteiApiService)
  snackbar = inject(MatSnackBar)
  fileForm: FormGroup;
  selectedFile: File | null = null;
  @Input() stapelId! : string | null
  private offcanvasInstance: any

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.fileForm = this.fb.group({});
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input?.files[0];
      console.log('Datei ausgewählt:', this.selectedFile);
    }
  }

  uploadData(): void {
    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      formData.append('stapelId', this.stapelId!)
      this.karteiService.postKartenFile(formData).subscribe({
        next: (resp) => {
          console.log(resp)
          this.snackbar.open(resp, "schließen", {
            duration: 3500
          })
        },
        error: (resp) =>  {
          this.snackbar.open(resp, "schließen", {
            duration: 3500
          })
        }
      })
    } else {
      console.warn('Keine Datei ausgewählt.');
    }
  }

  showFormatCanvas() {
    let offcanvasElement = document.getElementById('offcanvas');
    this.offcanvasInstance = new bootstrap.Offcanvas(offcanvasElement);
    this.offcanvasInstance.show();
  }

  hideFormatCanvas() {
    this.offcanvasInstance.hide()
  }
}
