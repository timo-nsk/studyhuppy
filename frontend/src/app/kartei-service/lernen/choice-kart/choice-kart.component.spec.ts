import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChoiceKartComponent } from './choice-kart.component';

describe('ChoiceKartComponent', () => {
  let component: ChoiceKartComponent;
  let fixture: ComponentFixture<ChoiceKartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChoiceKartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChoiceKartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
