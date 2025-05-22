import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KarteBearbeitenComponent } from './karte-bearbeiten.component';

describe('KarteBearbeitenComponent', () => {
  let component: KarteBearbeitenComponent;
  let fixture: ComponentFixture<KarteBearbeitenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KarteBearbeitenComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KarteBearbeitenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
