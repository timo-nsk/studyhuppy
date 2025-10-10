import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanWeekComponent } from './week.component';
import {provideHttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

describe('WeekComponent', () => {
  let component: PlanWeekComponent;
  let fixture: ComponentFixture<PlanWeekComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanWeekComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {},
      },
      provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlanWeekComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
