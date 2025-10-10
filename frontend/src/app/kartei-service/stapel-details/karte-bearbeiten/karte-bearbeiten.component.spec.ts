import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KarteBearbeitenComponent } from './karte-bearbeiten.component';
import {provideHttpClient} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

describe('KarteBearbeitenComponent', () => {
  let component: KarteBearbeitenComponent;
  let fixture: ComponentFixture<KarteBearbeitenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KarteBearbeitenComponent, ReactiveFormsModule, FormsModule],
      providers: [provideHttpClient()]
    }).compileComponents();

    fixture = TestBed.createComponent(KarteBearbeitenComponent);
    component = fixture.componentInstance;

    // Input-Mock setzen
    component.stapelId = 'stapel123';
    component.karteToEdit = {
      fachId: 'fach123',
      frage: 'Testfrage?',
      antwort: 'Testantwort',
      notiz: 'Testnotiz',
      antworten: [
        { wahrheit: true, antwort: 'Antwort1' },
        { wahrheit: false, antwort: 'Antwort2' }
      ]
    } as any; // cast als any oder richtige Interface

    fixture.detectChanges(); // ruft ngOnInit auf
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
