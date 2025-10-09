import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanWeekComponent } from './week.component';

describe('WeekComponent', () => {
  let component: PlanWeekComponent;
  let fixture: ComponentFixture<PlanWeekComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanWeekComponent]
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
