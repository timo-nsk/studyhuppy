import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KarteImportComponent } from './karte-import.component';
import {provideHttpClient} from '@angular/common/http';

describe('KarteImportComponent', () => {
  let component: KarteImportComponent;
  let fixture: ComponentFixture<KarteImportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KarteImportComponent],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KarteImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
