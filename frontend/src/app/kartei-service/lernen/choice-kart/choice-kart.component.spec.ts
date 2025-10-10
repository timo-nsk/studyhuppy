import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChoiceKartComponent } from './choice-kart.component';
import {provideHttpClient} from '@angular/common/http';

describe('ChoiceKartComponent', () => {
  let component: ChoiceKartComponent;
  let fixture: ComponentFixture<ChoiceKartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChoiceKartComponent],
      providers: [provideHttpClient()]
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
