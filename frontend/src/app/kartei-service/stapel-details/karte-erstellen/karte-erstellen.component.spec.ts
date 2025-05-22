import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KarteErstellenComponent } from './karte-erstellen.component';

describe('KarteErstellenComponent', () => {
  let component: KarteErstellenComponent;
  let fixture: ComponentFixture<KarteErstellenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KarteErstellenComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KarteErstellenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
