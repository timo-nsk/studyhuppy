import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActuatorServiceComponent } from './actuator-service.component';

describe('ActuatorServiceComponent', () => {
  let component: ActuatorServiceComponent;
  let fixture: ComponentFixture<ActuatorServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActuatorServiceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActuatorServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
