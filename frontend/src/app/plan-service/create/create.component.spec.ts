import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanCreateComponent } from './create.component';

describe('CreateComponent', () => {
  let component: PlanCreateComponent;
  let fixture: ComponentFixture<PlanCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlanCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
