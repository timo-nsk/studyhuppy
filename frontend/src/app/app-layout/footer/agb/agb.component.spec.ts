import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgbComponent } from './agb.component';
import {ActivatedRoute} from '@angular/router';

describe('AgbComponent', () => {
  let component: AgbComponent;
  let fixture: ComponentFixture<AgbComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgbComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {},
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
