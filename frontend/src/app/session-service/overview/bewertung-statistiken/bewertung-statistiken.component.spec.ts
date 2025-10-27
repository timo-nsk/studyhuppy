import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BewertungStatistikenComponent } from './bewertung-statistiken.component';

describe('BewertungStatistikenComponent', () => {
  let component: BewertungStatistikenComponent;
  let fixture: ComponentFixture<BewertungStatistikenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BewertungStatistikenComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BewertungStatistikenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
